package moe.banana.mmio.model;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.Configuration;
import moe.banana.mmio.service.Gank;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

@Module
public class ArticleSource {

    @Provides
    public static ArticleSource provideArticleSource(
            Gank api,
            @Configuration(key = "articleCategory") Article.Category category,
            @Configuration(key = "articlePageSize") int pageSize) {
        return new ArticleSource(api, category, pageSize);
    }

    public static final int STATE_NO_CHANGE = 0x00;
    public static final int STATE_REFRESH = 0x01;
    public static final int STATE_MORE_ITEMS = 0x02;

    private final Gank api;
    private final Article.Category category;
    private final int pageSize;
    private final ArrayList<Article> data = new ArrayList<>();
    private final Subject<Integer, Integer> subject = BehaviorSubject.create(-1).toSerialized();

    private ArticleSource(Gank api, Article.Category category, int pageSize) {
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
        adapter.notifyDataSetChanged();
        return subject.filter(x -> !(x != -1 && x < data.size() && data.get(x) != null))
                .concatMap(position -> {
                    if (position == -1) {
                        return api.listArticlesByCategory(category, pageSize, 1)
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
                                            return STATE_NO_CHANGE;
                                        } else {
                                            data.addAll(articles.subList(0, lastTop));
                                            adapter.notifyItemRangeInserted(0, lastTop);
                                            return STATE_REFRESH;
                                        }
                                    }
                                    data.addAll(articles);
                                    adapter.notifyItemRangeInserted(0, articles.size());
                                    return STATE_REFRESH;
                                });
                    } else {
                        int pageNumber = position / pageSize;
                        return api.listArticlesByCategory(category, pageSize, pageNumber + 1)
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
                                    return STATE_MORE_ITEMS;
                                });
                    }
                });
    }

}
