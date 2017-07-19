package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.util.StringUtil;

public class AnswerSummaryResult extends BaseSummaryResult {
    protected Long questionId;
    protected String questionTitle;
    protected Profile questionProfile;
    protected Integer type;
    protected AnswerDetailResult answer;
    protected String createTime;
    protected String updateTime;
    protected boolean isDeleted;

    public AnswerSummaryResult(Answer answer) {
        this.questionId = answer.getQuestion().getId();
        this.questionTitle = answer.getQuestion().getTitle();
        this.questionProfile = answer.getQuestion().getProfile();
        this.type = Item.ITEM_TYPE_ANSWER;
        this.answer = new AnswerDetailResult(answer);
        this.answer.setContent(StringUtil.extractHTMLSummary(answer.getContent()));
        this.createTime = answer.getCreateTime();
        this.updateTime = answer.getUpdateTime();
        this.isDeleted = answer.isDeleted();
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

    public AnswerDetailResult getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDetailResult answer) {
        this.answer = answer;
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
