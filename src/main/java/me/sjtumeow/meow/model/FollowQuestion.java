package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class FollowQuestion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Question question;

    public FollowQuestion() {
    }

    public FollowQuestion(User user, Question question) {
        this.user = user;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
