package moe.banana.mmio;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import moe.banana.mmio.model.Article;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiUnitTest {

    private TestApiComponent component;

    @Before
    public void init() {
        component = DaggerTestApiComponent.create();
    }

    @Test
    public void singularity() throws Exception {
        assertTrue(component.api() == component.api());
    }

    @Test
    public void listArticles() throws Exception {
        TestSubscriber<Article> subscriber = TestSubscriber.create();
        component.api()
                .listArticlesByCategory(Article.Category.all, 10, 1)
                .flatMap(body -> Observable.from(body.results))
                .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        List<Article> articles = subscriber.getOnNextEvents();
        assertThat(articles.size(), is(10));
    }

}