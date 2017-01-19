package moe.banana.mmio;

import dagger.Component;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.scope.PresenterScope;
import moe.banana.mmio.view.ArticleViewModel;

@PresenterScope
@Component(modules = {
        ArticleSource.class,
        ArticlePresenterFragment.class
}, dependencies = {
        AppComponent.class
})
public interface ArticlePresenterComponent extends PresenterComponent<ArticleViewModel, ArticlePresenter> {

}
