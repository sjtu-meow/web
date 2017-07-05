package me.sjtumeow.meow.model;

import javax.persistence.Entity;

@Entity
public class Article extends Item {
    
	private static final long serialVersionUID = 1L;
	
	String title, content, cover;
	
	public Article() {
        type = Item.ITEM_TYPE_ARTICLE;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
