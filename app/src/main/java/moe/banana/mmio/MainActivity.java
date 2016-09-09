package moe.banana.mmio;

import android.databinding.DataBindingUtil;

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
    interface Controller {
        MainPresenter presenter();
    }

    @Override
    public MainPresenter createPresenter() {
        return DaggerMainActivity_Controller.builder()
                .appComponent(App.from(this))
                .mainActivity(this)
                .build()
                .presenter();
    }
}
