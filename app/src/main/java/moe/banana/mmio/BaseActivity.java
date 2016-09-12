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
import moe.banana.mmio.presenter.ActivityPresenterDelegate;
import moe.banana.mmio.presenter.BasePresenter;
import moe.banana.mmio.presenter.PresenterComponent;
import moe.banana.mmio.scope.ActivityScope;

@Module
public abstract class BaseActivity<VM extends ViewDataBinding, PRESENTER extends BasePresenter> extends AppCompatActivity {

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

    private PresenterComponent<VM, PRESENTER> mComponent;
    private ActivityPresenterDelegate<PRESENTER> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponent = createComponent(savedInstanceState);
        mPresenter = new ActivityPresenterDelegate<>(mComponent.presenter());
        mPresenter.onCreate(savedInstanceState);
    }

    public abstract PresenterComponent<VM, PRESENTER> createComponent(@Nullable Bundle savedInstanceState);

    public PRESENTER getPresenter() {
        return mComponent.presenter();
    }

    public VM getViewModel() {
        return mComponent.vm();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPresenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().setVariable(BR.presenter, getPresenter());
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getViewModel().setVariable(BR.presenter, null);
        mPresenter.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
