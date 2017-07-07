package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Article;

public class ArticleSummaryResult {
	protected Long id;
	protected String title;
	protected String summary;
	protected String cover;
	protected Integer likeCount;
    protected Integer commentCount;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;
    
    public ArticleSummaryResult(Article article) {
    	id = article.getId();
    	title = article.getTitle();
    	summary = article.getSummary();
    	cover = article.getCover();
    	likeCount = article.getLikeCount();
        commentCount = article.getCommentCount();
        createTime = article.getCreateTime();
        updateTime = article.getUpdateTime();
        isDeleted = article.isDeleted();
    }
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public String getCover() {
		return cover;
	}
	
	public void setCover(String cover) {
		this.cover = cover;
	}
	
	public Integer getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
