package moe.banana.mmio.service;

import moe.banana.mmio.model.Article;
import moe.banana.mmio.model.Category;
import moe.banana.mmio.model.ListResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Gank {

    @GET("data/{category}/{size}/{page}")
    Observable<ListResult<Article>> listArticlesByCategory(
            @Path("category") Category category,
            @Path("size") int pageSize, @Path("page") int pageNumber);
}
