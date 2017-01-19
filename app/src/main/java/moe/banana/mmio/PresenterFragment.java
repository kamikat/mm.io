package moe.banana.mmio;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.PresenterScope;

@Module
public abstract class PresenterFragment<VM extends ViewDataBinding, PRESENTER extends Presenter> extends Fragment {

    @Provides
    @PresenterScope
    public VM provideViewModel() {
        return DataBindingUtil.bind(getView());
    }

    private PresenterComponent<VM, PRESENTER> component;

    private VM vm;
    private PRESENTER presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        component = onCreateComponent(view, savedInstanceState);
        if (component != null) {
            vm = component.vm();
            presenter = component.presenter();
            presenter.onCreate(savedInstanceState);
            onComponentCreated(component);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (vm != null) {
            vm.setVariable(BR.presenter, presenter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (vm != null) {
            vm.setVariable(BR.presenter, null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Nullable
    public PresenterComponent<VM, PRESENTER> onCreateComponent(View view, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @CallSuper
    public void onComponentCreated(PresenterComponent<VM, PRESENTER> component) {
        // do nothing
    }

    @Nullable
    public final PresenterComponent<VM, PRESENTER> getComponent() {
        return component;
    }

    @Provides
    public Context provideContext() {
        return getContext();
    }

    @Provides
    public Activity provideActivity() {
        return getActivity();
    }

    @Provides
    public LayoutInflater provideInflater(Context context) {
        return LayoutInflater.from(context);
    }
}
