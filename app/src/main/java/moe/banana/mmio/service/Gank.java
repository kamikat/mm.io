package moe.banana.mmio.service;

import moe.banana.mmio.model.Article;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Gank {

    @GET("data/{category}/{size}/{page}")
    Observable<ListResult<Article>> listArticlesByCategory(
            @Path("category") Article.Category category,
            @Path("size") int pageSize, @Path("page") int pageNumber);
}
