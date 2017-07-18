package me.sjtumeow.meow.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;

public class QuestionDetailResult {
	protected Long id;
	protected Integer type;
	protected Profile profile;
	protected String title;
	protected String content;
	protected List<AnswerDetailResult> answers;
	protected String createTime;
	protected String updateTime;
	protected boolean isDeleted;
	
	public QuestionDetailResult(Question question) {
		this.id = question.getId();
		this.type = Item.ITEM_TYPE_QUESTION;
		this.profile = question.getProfile();
		this.title = question.getTitle();
		this.content = question.getContent();
		this.createTime = question.getCreateTime();
		this.updateTime = question.getUpdateTime();
		this.isDeleted = question.isDeleted();
		this.answers = new ArrayList<AnswerDetailResult>();
		
		for (Answer answer: question.getAnswers()) {
			if (!answer.isDeleted())
				answers.add(new AnswerDetailResult(answer));
		}
		
		Collections.sort(answers, new Comparator<AnswerDetailResult>() {
            @Override
            public int compare(AnswerDetailResult lhs, AnswerDetailResult rhs) {
                Integer res = lhs.getCreateTime().compareTo(rhs.getCreateTime());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });
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

	public String getContent() {
		return content;
	}

	public List<AnswerDetailResult> getAnswers() {
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
	
	public void setType(Integer type) {
		this.type = type;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAnswers(List<AnswerDetailResult> answers) {
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
