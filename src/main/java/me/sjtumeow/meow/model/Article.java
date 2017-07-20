package me.sjtumeow.meow.model;

import javax.persistence.Entity;

@Entity
public class Article extends Item {

    private static final long serialVersionUID = 1L;

    String title, summary, content, cover;

    public Article() {
        type = Item.ITEM_TYPE_ARTICLE;
    }

    public Article(String title, String summary, String content, String cover) {
        type = Item.ITEM_TYPE_ARTICLE;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
