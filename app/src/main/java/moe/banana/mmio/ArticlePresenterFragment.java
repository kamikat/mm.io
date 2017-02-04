package moe.banana.mmio;

import dagger.Module;
import moe.banana.mmio.view.ArticleViewModel;

/**
 * Helper class representing any fragment capable with {@link ArticlePresenter}
 *
 * Workaround the problem of a generic typed class cannot be used as a valid dependency
 * in {@link dagger.Component} declaration
 */
@Module
public class ArticlePresenterFragment extends PresenterFragment<ArticleViewModel, ArticlePresenter> {

}
