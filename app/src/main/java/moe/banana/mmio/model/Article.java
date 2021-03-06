package moe.banana.mmio.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("unused")
public class Article implements Serializable {

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
}
