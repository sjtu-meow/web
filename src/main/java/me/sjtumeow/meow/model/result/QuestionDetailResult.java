package me.sjtumeow.meow.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;

public class QuestionDetailResult {
	protected Long id;
	protected Profile profile;
	protected List<Comment> comments;
	protected Integer likeCount;
	protected Integer commentCount;
	protected String title;
	protected String content;
	protected List<Answer> answers;
	protected String createTime;
	protected String updateTime;
	protected boolean isDeleted;
	
	public QuestionDetailResult(Question question) {
		this.id = question.getId();
		this.profile = question.getProfile();
		this.likeCount = question.getLikeCount();
		this.commentCount = question.getCommentCount();
		this.title = question.getTitle();
		this.content = question.getContent();
		this.createTime = question.getCreateTime();
		this.updateTime = question.getUpdateTime();
		this.isDeleted = question.isDeleted();
		this.comments = new ArrayList<Comment>();
		this.answers = new ArrayList<Answer>();
		
		for (Comment comment: question.getComments()) {
			if (!comment.isDeleted())
				comments.add(comment);
		}
		
		Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                Integer res = lhs.getUpdateTime().compareTo(rhs.getUpdateTime());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });
		
		for (Answer answer: question.getAnswers()) {
			if (!answer.isDeleted())
				answers.add(answer);
		}
		
		Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer lhs, Answer rhs) {
                Integer res = lhs.getUpdateTime().compareTo(rhs.getUpdateTime());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });
	}

	public Long getId() {
		return id;
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

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public List<Answer> getAnswers() {
		return answers;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
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
