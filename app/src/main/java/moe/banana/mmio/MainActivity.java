package moe.banana.mmio;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.modules.LayoutManagers;
import moe.banana.mmio.presenter.MainPresenter;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.MainViewModel;

@Module
public class MainActivity extends AppCompatActivity {

    @ActivityScope
    @Component(
            modules = {MainActivity.class, LayoutManagers.class},
            dependencies = {AppComponent.class})
    interface Controller {
        MainViewModel vm();
        MainPresenter presenter();
    }

    @Provides
    @ActivityScope
    public Context provideContext() {
        return this;
    }

    @Provides
    @ActivityScope
    public MainViewModel provideViewModel() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Provides
    @ActivityScope
    public RecyclerView.Adapter<?> provideAdapter() {
        return null; // TODO implement adapter...
    }

    @Provides
    @ActivityScope
    public MainPresenter providePresenter(
            Gank api, MainViewModel vm,
            RecyclerView.Adapter<?> adapter, @LayoutManagers.Linear RecyclerView.LayoutManager layout) {
        return new MainPresenter() {

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return layout;
            }

            @Override
            public RecyclerView.Adapter<?> getAdapter() {
                return adapter;
            }
        };
    }

    Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = DaggerMainActivity_Controller.builder()
                .appComponent(App.from(this))
                .mainActivity(this)
                .build();
        mController.vm().setPresenter(mController.presenter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.vm().setPresenter(null);
    }
}
