package moe.banana.mmio.presenter;

import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.MainViewModel;

@ActivityScope
public class MainPresenter extends ActivityPresenter {

    @Inject public Gank api;
    @Inject public MainViewModel vm;
    @Inject public ArticleAdapter adapter;
    @Inject public LinearLayoutManager layoutManager;

    @Inject MainPresenter(MainViewModel vm) {
        vm.setPresenter(this); // TODO detach the presenter (not necessary for activity presenter)
    }
}
