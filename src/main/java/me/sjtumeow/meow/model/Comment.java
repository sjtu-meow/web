package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment extends Item {

    private static final long serialVersionUID = 1L;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @Column(nullable = false)
    private String content;

    public Comment() {
        type = Item.ITEM_TYPE_COMMENT;
    }

    public Comment(Item item, Profile profile, String content) {
        type = Item.ITEM_TYPE_COMMENT;
        this.item = item;
        this.profile = profile;
        this.content = content;
    }

    public Item getItem() {
        return item;
    }

    public String getContent() {
        return content;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
