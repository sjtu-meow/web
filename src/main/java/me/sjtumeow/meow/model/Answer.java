package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends Item {
    private String content;

    @JsonBackReference
    @ManyToOne(optional = false)
    Question question;

}
