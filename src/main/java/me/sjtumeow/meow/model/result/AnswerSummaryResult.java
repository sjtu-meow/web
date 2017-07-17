package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Profile;

public class AnswerSummaryResult {
	protected Long questionId;
	protected String questionTitle;
	protected Profile questionProfile;
	protected Answer answer;
    
    public AnswerSummaryResult(Answer answer) {
    	this.questionId = answer.getQuestion().getId();
    	this.questionTitle = answer.getQuestion().getTitle();
    	this.questionProfile = answer.getQuestion().getProfile();
    	this.answer = answer;
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

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
    
}
