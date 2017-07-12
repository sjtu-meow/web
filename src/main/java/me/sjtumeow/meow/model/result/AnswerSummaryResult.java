package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;

public class AnswerSummaryResult {
	protected Long questionId;
	protected String questionTitle;
	protected Answer answer;
    
    public AnswerSummaryResult(Answer answer) {
    	this.questionId = answer.getQuestion().getId();
    	this.questionTitle = answer.getQuestion().getTitle();
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

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
    
}
