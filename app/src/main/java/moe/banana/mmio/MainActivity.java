package moe.banana.mmio;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.module.LayoutManagers;
import moe.banana.mmio.presenter.MainPresenter;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.view.MainViewModel;

@Module
public class MainActivity extends BaseActivity<MainPresenter> {

    @Provides
    @ActivityScope
    public MainViewModel provideViewModel() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @ActivityScope
    @Component(
            modules = {MainActivity.class, LayoutManagers.class},
            dependencies = {AppComponent.class})
    interface Presenter {
        MainPresenter get();
    }

    @Override
    public MainPresenter createPresenter(@Nullable Bundle savedInstanceState) {
        return DaggerMainActivity_Presenter.builder()
                .appComponent(App.from(this))
                .mainActivity(this)
                .build()
                .get();
    }
}
