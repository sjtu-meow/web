package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Formula;

@Entity
public class Answer extends Item {
    
	private static final long serialVersionUID = 1L;

	private String content;

	@JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    Question question;
	
	@Formula("question_id")
	Long questionId;
	
	public Answer() {
		type = Item.ITEM_TYPE_ANSWER;
	}
	
	public Answer(String content) {
		type = Item.ITEM_TYPE_ANSWER;
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
