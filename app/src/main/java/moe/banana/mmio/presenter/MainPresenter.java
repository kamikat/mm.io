package moe.banana.mmio.presenter;

import android.app.Activity;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import javax.inject.Inject;

import moe.banana.mmio.BR;
import moe.banana.mmio.R;
import moe.banana.mmio.misc.RxErrorFence;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.view.MainViewModel;
import rx.Subscription;

@ActivityScope
public class MainPresenter extends ActivityPresenter {

    ///
    // Binding Attributes
    ///

    @Inject public ArticleAdapter adapter;
    @Inject public RecyclerView.LayoutManager layoutManager;


    ///
    // Presenter
    ///

    @Inject Activity context;
    @Inject MainViewModel vm;
    @Inject ArticleSource source;

    private boolean isRefreshing;
    private Subscription subscription;

    @Inject
    MainPresenter() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscription = source.notifyChangesTo(adapter).doOnNext(state -> {
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
            Log.e("ArticleSource", "Exception loading data source", err);
            Snackbar.make(vm.getRoot(), R.string.error_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.action_retry, v -> fence.boom(err))
                    .show();
            return fence.build();
        }).retry().subscribe();
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    public void requestRefresh() {
        source.requestRefresh();
    }

    @Bindable
    public boolean getIsRefreshing() {
        return isRefreshing;
    }

    private void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        notifyPropertyChanged(BR.isRefreshing);
    }
}
