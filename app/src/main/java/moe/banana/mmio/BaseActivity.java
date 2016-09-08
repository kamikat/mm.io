package moe.banana.mmio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.presenter.ActivityPresenter;

@Module
public abstract class BaseActivity<PRESENTER extends ActivityPresenter> extends AppCompatActivity {

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

    private PRESENTER mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    public abstract PRESENTER createPresenter();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPresenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
