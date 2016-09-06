package moe.banana.mmio.model;

import java.io.Serializable;
import java.util.Date;

import rx.Observable;

@SuppressWarnings("unused")
public class Article implements Serializable {

    public enum Category {
        福利, Android, iOS, 休息视频, 拓展资源, 前端, all
    }

    public String _id;
    public String desc;
    public String url;
    public String source;
    public Category type;
    public String who;
    public boolean used;
    public Date createdAt;
    public Date publishedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return _id.equals(article._id);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    public static Observable<Article> stream(Category category) {
        // TODO how to stream?
        return null;
    }
}
