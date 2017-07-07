package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Profile;

public class QuestionSummaryResult {
	protected Long id;
	protected String title;
	protected String content;
    protected Profile profile;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;
    
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public Profile getProfile() {
		return profile;
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
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
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
