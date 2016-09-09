package moe.banana.mmio.presenter;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import javax.inject.Inject;

import moe.banana.mmio.BR;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.module.LayoutManagers;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.MainViewModel;
import rx.Subscription;

@ActivityScope
public class MainPresenter extends ActivityPresenter {

    ///
    // Binding Attributes
    ///

    @Inject public ArticleAdapter adapter;

    @Inject @LayoutManagers.Columns(2)
    public GridLayoutManager layoutManager;

    @Bindable public boolean isRefreshing;

    ///
    // Presenter
    ///

    @Inject Gank api;
    @Inject MainViewModel vm;
    @Inject ArticleSource source;

    private Subscription subscription;

    @Inject
    MainPresenter() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm.setPresenter(this);
        adapter.setDataSource(source);
        subscription = source.notifyChangesTo(adapter).doOnNext(state -> {
            switch (state) {
                case ArticleSource.STATE_REFRESH:
                case ArticleSource.STATE_NO_CHANGE:
                    setIsRefreshing(false);
                    break;
            }
        }).subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRefresh();
    }

    public void requestRefresh() {
        setIsRefreshing(true);
        source.requestRefresh();
    }

    private void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        notifyPropertyChanged(BR.isRefreshing);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        vm.setPresenter(null);
        super.onDestroy();
    }
}
