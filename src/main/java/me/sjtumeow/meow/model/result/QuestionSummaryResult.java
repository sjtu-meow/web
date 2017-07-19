package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.util.TimeComparableObject;

public class QuestionSummaryResult extends TimeComparableObject {
	protected Long id;
	protected Integer type;
	protected Profile profile;
	protected String title;
	protected Integer answerCount;
	protected String createTime;
	protected String updateTime;
	protected boolean isDeleted;
	
	public QuestionSummaryResult(Question question) {
		this.id = question.getId();
		this.type = Item.ITEM_TYPE_QUESTION;
		this.profile = question.getProfile();
		this.title = question.getTitle();
		this.createTime = question.getCreateTime();
		this.updateTime = question.getUpdateTime();
		this.isDeleted = question.isDeleted();
		this.answerCount = 0;
		
		for (Answer answer: question.getAnswers()) {
			if (!answer.isDeleted())
				++answerCount;
		}
	}

	public Long getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public Profile getProfile() {
		return profile;
	}

	public String getTitle() {
		return title;
	}

	public Integer getAnswerCount() {
		return answerCount;
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

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
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
