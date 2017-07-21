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
public class PushArchive extends BaseEntity {

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

    private String text;

    public PushArchive() {
    }

    public PushArchive(Item item, String text) {
        this.item = item;
        this.itemType = item.getType();
        this.text = text;
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

    public String getText() {
        return text;
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

    public void setText(String text) {
        this.text = text;
    }

}
