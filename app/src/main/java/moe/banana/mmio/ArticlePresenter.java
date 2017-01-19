package moe.banana.mmio;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import moe.banana.mmio.misc.BindingViewHolder;
import moe.banana.mmio.misc.RxErrorFence;
import moe.banana.mmio.model.Article;
import moe.banana.mmio.model.DataSource;
import moe.banana.mmio.scope.PresenterScope;
import moe.banana.mmio.view.ArticleViewModel;

@PresenterScope
public class ArticlePresenter extends BaseObservable implements Presenter {

    @Inject Context context;
    @Inject LayoutInflater inflater;
    @Inject ArticleViewModel vm;
    @Inject DataSource<Article> source;
    @Inject Picasso picasso;

    @Inject ArticlePresenter() {

    }

    ///
    // Binding Attributes
    ///

    private boolean isRefreshing;

    @Bindable
    public boolean getIsRefreshing() {
        return isRefreshing;
    }

    public void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        notifyPropertyChanged(BR.isRefreshing);
    }

    private RecyclerView.Adapter<?> adapter;

    @Bindable
    public RecyclerView.Adapter<?> getAdapter() {
        return adapter;
    }

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(context, 2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        adapter = new RecyclerView.Adapter<BindingViewHolder>() {
            @Override
            public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return BindingViewHolder.create(LayoutInflater.from(context).inflate(R.layout.list_item_article, parent, false));
            }

            @Override
            public void onBindViewHolder(BindingViewHolder holder, int position) {
                holder.binding.setVariable(BR.article, source.getItem(position));
                holder.binding.executePendingBindings();
            }

            @Override
            public int getItemCount() {
                return source.getItemCount() == 0 ? 0 : source.getItemCount() + 1;
            }
        };

        // Bind ArticleSource states
        source.notifyChangesTo(adapter, observable -> observable.doOnNext(articles -> {
            for (Article article : articles) {
                picasso.load(article.url)
                        .resizeDimen(R.dimen.thumbnail_size_w, R.dimen.thumbnail_size_h)
                        .centerCrop()
                        .fetch();
            }
        })).doOnNext(state -> {
            switch (state & DataSource.MASK_ACTION_FLAG) {
                case DataSource.FLAG_ACTION_REFRESH:
                    if ((state & DataSource.MASK_NOTIFY_FLAG) == DataSource.FLAG_NOTIFY_ONGOING) {
                        setIsRefreshing(true);
                    } else {
                        setIsRefreshing(false);
                        if ((state & DataSource.MASK_CHANGE_FLAG) != DataSource.FLAG_CHANGED) {
                            Snackbar.make(vm.getRoot(), R.string.no_updates, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case DataSource.FLAG_ACTION_FORWARD:
                    break;
            }
        }).onErrorResumeNext(err -> {
            setIsRefreshing(false);
            RxErrorFence fence = RxErrorFence.create();
            Log.e("ArticleSource", "Cannot load data source", err);
            Snackbar.make(vm.getRoot(), R.string.error_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.action_retry, v -> fence.boom(err))
                    .show();
            return fence.build();
        }).retry().subscribe();
    }

    public void requestRefresh() {
        source.requestRefresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {

    }
}
