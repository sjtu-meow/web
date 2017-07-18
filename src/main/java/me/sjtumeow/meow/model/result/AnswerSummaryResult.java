package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.util.StringUtil;

public class AnswerSummaryResult {
	protected Long questionId;
	protected String questionTitle;
	protected Profile questionProfile;
	protected Integer type;
	protected Answer answer;
    
    public AnswerSummaryResult(Answer answer) {
    	this.questionId = answer.getQuestion().getId();
    	this.questionTitle = answer.getQuestion().getTitle();
    	this.questionProfile = answer.getQuestion().getProfile();
    	this.type = Item.ITEM_TYPE_ANSWER;
    	this.answer = answer;
    	this.answer.setContent(StringUtil.extractHTMLSummary(answer.getContent()));
    }
    
	public Long getQuestionId() {
		return questionId;
	}
	
	public String getQuestionTitle() {
		return questionTitle;
	}
	
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	
	public Profile getQuestionProfile() {
		return questionProfile;
	}

	public void setQuestionProfile(Profile questionProfile) {
		this.questionProfile = questionProfile;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
    
}
