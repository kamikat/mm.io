package moe.banana.mmio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.App;
import moe.banana.mmio.AppComponent;
import moe.banana.mmio.Configuration;
import moe.banana.mmio.ConfigurationModule;
import moe.banana.mmio.R;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.model.Category;
import moe.banana.mmio.presenter.ArticlePresenter;
import moe.banana.mmio.scope.PresenterScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.view.ArticleViewModel;

@Module(includes = {ConfigurationModule.class})
public class HomeFragment extends PresenterFragment<ArticleViewModel, ArticlePresenter> {

    @PresenterScope
    @Component(modules = {HomeFragment.class}, dependencies = {AppComponent.class})
    interface Presenter extends PresenterComponent<ArticleViewModel, ArticlePresenter> { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_article, container, false);
    }

    @Override
    public Presenter onCreateComponent(View view, @Nullable Bundle savedInstanceState) {
        return DaggerHomeFragment_Presenter.builder()
                .appComponent(App.from(this))
                .homeFragment(this)
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add additional view properties
        getComponent().vm().setLayoutManager(new GridLayoutManager(getContext(), 2));
        getComponent().vm().setItemFactory(parent ->
                LayoutInflater.from(getContext()).inflate(R.layout.list_item_article, parent, false));
    }

    @Provides
    @PresenterScope
    public static ArticleSource provideArticleSource(Configuration conf, Gank api) {
        return ArticleSource.create(api, Category.福利, conf.pageSize());
    }

}
