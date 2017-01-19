package moe.banana.mmio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.banana.mmio.App;
import moe.banana.mmio.ArticlePresenterComponent;
import moe.banana.mmio.ArticlePresenterFragment;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.DaggerArticlePresenterComponent;
import moe.banana.mmio.R;
import moe.banana.mmio.model.Category;

public class HomeFragment extends ArticlePresenterFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_article, container, false);
    }

    @Override
    public ArticlePresenterComponent onCreateComponent(View view, @Nullable Bundle savedInstanceState) {
        return DaggerArticlePresenterComponent.builder()
                .appComponent(App.from(this))
                .articleSource(new ArticleSource(Category.福利, 20))
                .articlePresenterFragment(this)
                .build();
    }
}
