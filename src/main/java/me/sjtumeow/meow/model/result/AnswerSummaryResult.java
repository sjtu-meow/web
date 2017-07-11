package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;

public class AnswerSummaryResult {
	protected Long id;
	protected String title;
	protected Answer answer;
    
    public AnswerSummaryResult(Answer answer) {
    	this.id = answer.getQuestion().getId();
    	this.title = answer.getQuestion().getTitle();
    	this.answer = answer;
    }
    
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
    
}
