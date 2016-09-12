package moe.banana.mmio;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.misc.ItemViewFactory;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.model.Category;
import moe.banana.mmio.presenter.MainPresenter;
import moe.banana.mmio.presenter.PresenterComponent;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.MainViewModel;

@Module(includes = {ConfigurationModule.class})
public class MainActivity extends BaseActivity<MainViewModel, MainPresenter> {

    @Provides
    @ActivityScope
    public MainViewModel provideViewModel() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Provides
    @ActivityScope
    public static ArticleSource provideArticleSource(Configuration conf, Gank api) {
        return ArticleSource.create(api, Category.福利, conf.pageSize());
    }

    @Provides
    public RecyclerView.LayoutManager provideRecyclerLayout() {
        return new GridLayoutManager(this, 2);
    }

    /**
     * @param inflater injected layout inflater
     * @return an {@link ItemViewFactory} creates view model to bind article objects
     */
    @Provides
    @ActivityScope
    public static ItemViewFactory provideItemViewFactory(LayoutInflater inflater) {
        return parent -> inflater.inflate(R.layout.item_article, parent, false);
    }

    @ActivityScope
    @Component(modules = {MainActivity.class}, dependencies = {AppComponent.class})
    interface Presenter extends PresenterComponent<MainViewModel, MainPresenter> { }

    @Override
    public Presenter createComponent(@Nullable Bundle savedInstanceState) {
        return DaggerMainActivity_Presenter.builder()
                .appComponent(App.from(this))
                .mainActivity(this)
                .build();
    }

}
