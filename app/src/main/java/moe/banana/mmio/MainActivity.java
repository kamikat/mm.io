package moe.banana.mmio;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.model.Article;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.presenter.MainPresenter;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.MainViewModel;

@Module(includes = {ConfigurationModule.class})
public class MainActivity extends BaseActivity<MainPresenter> {

    @Provides
    @ActivityScope
    public MainViewModel provideViewModel() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Provides
    @ActivityScope
    public static ArticleSource provideArticleSource(Configuration conf, Gank api) {
        return ArticleSource.create(api, Article.Category.福利, conf.pageSize());
    }

    @Provides
    public RecyclerView.LayoutManager provideRecyclerLayout() {
        return new GridLayoutManager(this, 2);
    }

    @ActivityScope
    @Component(modules = {MainActivity.class}, dependencies = {AppComponent.class})
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
