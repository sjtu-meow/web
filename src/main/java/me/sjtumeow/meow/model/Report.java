package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Report extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @Formula("item_id")
    private Long itemId;

    private Integer itemType;

    @ManyToOne(optional = false)
    private Profile profile;

    private String reason;

    @Column(nullable = false)
    private Boolean closed;

    public Report() {
        this.closed = false;
    }

    public Report(Item item, Profile profile, String reason) {
        this.item = item;
        this.itemType = item.getType();
        this.profile = profile;
        this.reason = reason;
        this.closed = false;
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Long getItemId() {
        return itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getReason() {
        return reason;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
        this.itemType = item.getType();
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

}
