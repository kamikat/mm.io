package moe.banana.mmio.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.BR;
import moe.banana.mmio.scope.PresenterScope;

@Module
public abstract class PresenterFragment<VM extends ViewDataBinding, PRESENTER> extends Fragment {

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
        vm = component.vm();
        presenter = component.presenter();
    }

    public abstract PresenterComponent<VM, PRESENTER> onCreateComponent(View view, @Nullable Bundle savedInstanceState);

    public final PresenterComponent<VM, PRESENTER> getComponent() {
        return component;
    }

    @Override
    public void onStart() {
        super.onStart();
        vm.setVariable(BR.presenter, presenter);
    }

    @Override
    public void onStop() {
        super.onStop();
        vm.setVariable(BR.presenter, null);
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
