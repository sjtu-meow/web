package me.sjtumeow.meow.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    public static int ITEM_TYPE_MOMENT = 0;
    public static int ITEM_TYPE_ARTICLE = 1;
    public static int ITEM_TYPE_QUESTION = 2;
    public static int ITEM_TYPE_ANSWER = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    Long id;
    
    @Column(nullable = false)
    Integer type;

    @OneToOne
    @JoinColumn
    Profile profile;
    
    @OneToMany(mappedBy = "item")
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
