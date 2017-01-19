package moe.banana.mmio.model;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.PresenterScope;
import moe.banana.mmio.service.Gank;

@Module
public class ArticleSource {

    private final Category category;
    private final int pageSize;

    public ArticleSource(Category category, int pageSize) {
        this.category = category;
        this.pageSize = pageSize;
    }

    @Provides
    @PresenterScope
    public DataSource<Article> provideArticleSource(Gank api) {
        return new ArticleSourceImpl(api, category, pageSize);
    }
}
