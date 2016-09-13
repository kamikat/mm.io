package moe.banana.mmio.presenter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import javax.inject.Inject;

import moe.banana.mmio.BR;
import moe.banana.mmio.R;
import moe.banana.mmio.misc.BindingViewHolder;
import moe.banana.mmio.misc.ItemViewFactory;
import moe.banana.mmio.misc.RxErrorFence;
import moe.banana.mmio.misc.RxLifecycleDelegate;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.scope.PresenterScope;
import moe.banana.mmio.view.MainViewModel;

@PresenterScope
public class MainPresenter extends BaseObservable {

    ///
    // Binding Attributes
    ///

    public final RecyclerView.Adapter<?> adapter;
    public final RecyclerView.LayoutManager layoutManager;

    private boolean isRefreshing;

    @Bindable
    public boolean getIsRefreshing() {
        return isRefreshing;
    }

    private void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        notifyPropertyChanged(BR.isRefreshing);
    }

    ///
    // Presenter
    ///

    private final ArticleSource source;

    @Inject
    MainPresenter(
            MainViewModel vm, ArticleSource source, RxLifecycleDelegate lifecycle,
            ItemViewFactory itemViewFactory, RecyclerView.LayoutManager layoutManager) {

        // Create article adapter
        RecyclerView.Adapter<?> adapter = new RecyclerView.Adapter<BindingViewHolder>() {
            @Override
            public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return BindingViewHolder.create(itemViewFactory.createView(parent));
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
        source.notifyChangesTo(adapter).doOnNext(state -> {
            switch (state & ArticleSource.MASK_ACTION_FLAG) {
                case ArticleSource.FLAG_ACTION_REFRESH:
                    if ((state & ArticleSource.MASK_NOTIFY_FLAG) == ArticleSource.FLAG_NOTIFY_ONGOING) {
                        setIsRefreshing(true);
                    } else {
                        setIsRefreshing(false);
                        if ((state & ArticleSource.MASK_CHANGE_FLAG) != ArticleSource.FLAG_CHANGED) {
                            Snackbar.make(vm.getRoot(), R.string.no_updates, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ArticleSource.FLAG_ACTION_FORWARD:
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
        }).retry().lift(lifecycle.disposeOnDestroy()).subscribe();

        // Export variables
        this.source = source;
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    public void requestRefresh() {
        source.requestRefresh();
    }
}
