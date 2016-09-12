package moe.banana.mmio.model;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import moe.banana.mmio.service.Gank;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public class ArticleSource {

    public static ArticleSource create(Gank api, Category category, int pageSize) {
        return new ArticleSource(api, category, pageSize);
    }

    public static final int MASK_CHANGE_FLAG = 0x01; // 0b00000001
    public static final int MASK_NOTIFY_FLAG = 0x02; // 0b00001110
    public static final int MASK_ACTION_FLAG = 0xF0; // 0b11110000

    public static final int FLAG_ACTION_REFRESH = 0x10;
    public static final int FLAG_ACTION_FORWARD = 0x20;
    public static final int FLAG_NOTIFY_ONGOING = 0x02;
    public static final int FLAG_CHANGED        = 0x01;

    private final Gank api;
    private final Category category;
    private final int pageSize;
    private final ArrayList<Article> data = new ArrayList<>();
    private final Subject<Integer, Integer> subject = BehaviorSubject.create(-1).toSerialized(); // Auto-refresh

    private ArticleSource(Gank api, Category category, int pageSize) {
        this.api = api;
        this.category = category;
        this.pageSize = pageSize;
    }

    public int getItemCount() {
        return data.size();
    }

    public Article getItem(int position) {
        subject.onNext(position);
        return position < data.size() ? data.get(position) : null;
    }

    public void requestRefresh() {
        subject.onNext(-1);
    }

    public Observable<Integer> notifyChangesTo(RecyclerView.Adapter<?> adapter) {
        return subject.filter(x -> !(x >= 0 && x < data.size() && data.get(x) != null))
                .concatMap(position -> {
                    if (position == -1) {
                        return Observable.concat(
                                Observable.just(FLAG_ACTION_REFRESH | FLAG_NOTIFY_ONGOING),
                                api.listArticlesByCategory(category, pageSize, 1)
                                        .map(listResult -> listResult.results)
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
