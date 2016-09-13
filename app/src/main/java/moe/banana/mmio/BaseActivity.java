package moe.banana.mmio;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.misc.RxLifecycleDelegate;
import moe.banana.mmio.scope.ActivityScope;

@Module
public abstract class BaseActivity<VM extends ViewDataBinding, PRESENTER> extends AppCompatActivity {

    @Provides
    @ActivityScope
    public Context provideContext() {
        return this;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return this;
    }

    @Provides
    @ActivityScope
    public LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(this);
    }

    @Provides
    @ActivityScope
    public RxLifecycleDelegate provideRxLifecycle() {
        return lifecycleDelegate;
    }

    private RxLifecycleDelegate lifecycleDelegate = new RxLifecycleDelegate();
    private PresenterComponent<VM, PRESENTER> component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = createComponent(savedInstanceState);
    }

    public abstract PresenterComponent<VM, PRESENTER> createComponent(@Nullable Bundle savedInstanceState);

    public PRESENTER getPresenter() {
        return component.presenter();
    }

    public VM getViewModel() {
        return component.vm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().setVariable(BR.presenter, getPresenter());
    }

    @Override
    protected void onStop() {
        super.onStop();
        getViewModel().setVariable(BR.presenter, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleDelegate.onDestroy();
    }
}
