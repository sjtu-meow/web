package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "likeitem")
public class Like extends BaseEntity {

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
    private Item item;

    public Like() {
    }

    public Like(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
