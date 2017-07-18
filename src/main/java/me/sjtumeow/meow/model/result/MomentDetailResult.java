package me.sjtumeow.meow.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;

public class MomentDetailResult {
	protected Long id;
	protected Integer type;
	protected String content;
	protected List<Media> medias;
	protected Profile profile;
	protected List<Comment> comments;
    protected Integer likeCount;
    protected Integer commentCount;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;
    
    public MomentDetailResult(Moment moment) {
    	this.id = moment.getId();
    	this.type = Item.ITEM_TYPE_MOMENT;
    	this.content = moment.getContent();
    	this.medias = moment.getMedias();
    	this.profile = moment.getProfile();
    	this.likeCount = moment.getLikeCount();
    	this.commentCount = moment.getCommentCount();
    	this.createTime = moment.getCreateTime();
        this.updateTime = moment.getUpdateTime();
        this.isDeleted = moment.isDeleted();
        
        this.comments = new ArrayList<Comment>();
		for (Comment comment: moment.getComments()) {
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

	public String getContent() {
		return content;
	}

	public List<Media> getMedias() {
		return medias;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
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
