package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Profile;

public class ArticleSummaryResult {
	protected Long id;
	protected String title;
	protected String summary;
	protected String cover;
	protected Profile profile;
	protected Integer likeCount;
    protected Integer commentCount;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;
    
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
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
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
