package moe.banana.mmio.model;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import moe.banana.mmio.service.Gank;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

class ArticleSourceImpl implements DataSource<Article> {

    private final Gank api;
    private final Category category;
    private final int pageSize;
    private final ArrayList<Article> data = new ArrayList<>();
    private final Subject<Integer, Integer> subject = BehaviorSubject.create(-1).toSerialized(); // Auto-refresh

    ArticleSourceImpl(Gank api, Category category, int pageSize) {
        this.api = api;
        this.category = category;
        this.pageSize = pageSize;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Article getItem(int position) {
        subject.onNext(position);
        return position < data.size() ? data.get(position) : null;
    }

    public void requestRefresh() {
        subject.onNext(-1);
    }

    public Observable<Integer> notifyChangesTo(
            RecyclerView.Adapter<?> adapter,
            Observable.Transformer<List<? extends Article>, List<? extends Article>> afterReceive) {
        return subject.filter(x -> !(x >= 0 && x < data.size() && data.get(x) != null))
                .concatMap(position -> {
                    if (position == -1) {
                        return Observable.concat(
                                Observable.just(FLAG_ACTION_REFRESH | FLAG_NOTIFY_ONGOING),
                                api.listArticlesByCategory(category, pageSize, 1)
                                        .map(listResult -> listResult.results)
                                        .compose(afterReceive)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map(articles -> {
                                            if (data.size() > 0) {
                                                int lastTop = articles.indexOf(data.get(0));
                                                if (lastTop == -1) {
                                                    int size = data.size();
                                                    data.clear();
                                                    adapter.notifyItemRangeRemoved(0, size);
                                                } else if (lastTop == 0) {
                                                    return FLAG_ACTION_REFRESH;
                                                } else {
                                                    data.addAll(articles.subList(0, lastTop));
                                                    adapter.notifyItemRangeInserted(0, lastTop);
                                                    return FLAG_ACTION_REFRESH | FLAG_CHANGED;
                                                }
                                            }
                                            data.addAll(articles);
                                            adapter.notifyItemRangeInserted(0, articles.size());
                                            return FLAG_ACTION_REFRESH | FLAG_CHANGED;
                                        }));
                    } else {
                        int pageNumber = position / pageSize;
                        return Observable.concat(
                                Observable.just(FLAG_ACTION_FORWARD | FLAG_NOTIFY_ONGOING),
                                api.listArticlesByCategory(category, pageSize, pageNumber + 1)
                                        .map(listResult -> listResult.results)
                                        .compose(afterReceive)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map(articles -> {
                                            int i = pageNumber * pageSize;
                                            for (Article article : articles) {
                                                if (i < data.size()) {
                                                    data.set(i, article);
                                                    adapter.notifyItemChanged(i);
                                                } else {
                                                    data.add(article);
                                                    adapter.notifyItemInserted(i);
                                                }
                                                i++;
                                            }
                                            int end = Math.min((pageNumber + 1) * pageSize, data.size());
                                            if (i < end) {
                                                while (i < end) {
                                                    data.remove(i);
                                                    end--;
                                                }
                                                adapter.notifyItemRangeRemoved(i, (pageNumber + 1) * pageSize - i);
                                            }
                                            return FLAG_ACTION_FORWARD | FLAG_CHANGED;
                                        }));
                    }
                });
    }

}
