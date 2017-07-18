package me.sjtumeow.meow.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;

public class ArticleDetailResult {
	protected Long id;
    protected Integer type;
    protected String title;
    protected String summary;
    protected String content;
    protected String cover;
    protected Profile profile;
    protected List<Comment> comments;
    protected Integer likeCount;
    protected Integer commentCount;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;
    
    public ArticleDetailResult(Article article) {
    	this.id = article.getId();
    	this.type = Item.ITEM_TYPE_ARTICLE;
    	this.title = article.getTitle();
    	this.summary = article.getSummary();
    	this.content = article.getContent();
    	this.cover = article.getCover();
    	this.profile = article.getProfile();
    	this.likeCount = article.getLikeCount();
    	this.commentCount = article.getCommentCount();
    	this.createTime = article.getCreateTime();
    	this.updateTime = article.getUpdateTime();
    	this.isDeleted = article.isDeleted();
    	
        this.comments = new ArrayList<Comment>();
		for (Comment comment: article.getComments()) {
			if (!comment.isDeleted())
				this.comments.add(comment);
		}
		
		Collections.sort(this.comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                Integer res = lhs.getCreatedAt().compareTo(rhs.getCreatedAt());
                return res == 0 ? 0 : res / Math.abs(res);
            }
        });
    }

	public Long getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getContent() {
		return content;
	}

	public String getCover() {
		return cover;
	}

	public Profile getProfile() {
		return profile;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
