package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment extends BaseEntity {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

	@Column(nullable = false)
    private Long parent = 0L;
    
    @JsonBackReference
    @ManyToOne(optional = false)
    private Item item;

    @JsonBackReference
    @ManyToOne(optional = false)
    private Profile profile;

    @Column(nullable = false)
    private String content;
    
    public Comment(Item item, Profile profile, String content) {
    	this.item = item;
    	this.profile = profile;
    	this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
    
    public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
