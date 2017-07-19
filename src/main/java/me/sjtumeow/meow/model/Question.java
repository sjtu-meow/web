package me.sjtumeow.meow.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question extends Item {

    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private Set<Answer> answers;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private Set<FollowQuestion> followers;

    public Question() {
        type = Item.ITEM_TYPE_QUESTION;
    }

    public Question(String title, String content) {
        type = Item.ITEM_TYPE_QUESTION;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<FollowQuestion> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<FollowQuestion> followers) {
        this.followers = followers;
    }
}
