package moe.banana.mmio;

import org.junit.Before;
import org.junit.Test;

import moe.banana.mmio.model.Article;
import moe.banana.mmio.model.Category;
import moe.banana.mmio.service.ListResult;

import static org.junit.Assert.assertEquals;
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
        ListResult<Article> response =
                component.api().listArticlesByCategory(Category.all, 10, 1).toBlocking().first();
        assertEquals(response.results.size(), 10);
    }

}