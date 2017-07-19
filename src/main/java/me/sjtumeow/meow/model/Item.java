package me.sjtumeow.meow.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int ITEM_TYPE_MOMENT = 0;
    public static final int ITEM_TYPE_ARTICLE = 1;
    public static final int ITEM_TYPE_QUESTION = 2;
    public static final int ITEM_TYPE_ANSWER = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    Long id;

    @Column(nullable = false)
    Integer type;

    @OneToOne
    @JoinColumn
    Profile profile;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private Set<Comment> comments;

    @Formula("(SELECT COUNT(*) FROM likeitem l WHERE l.item_id = id)")
    Integer likeCount;

    @Formula("(SELECT COUNT(*) FROM comment c WHERE c.item_id = id AND c.deleted_at IS NULL)")
    Integer commentCount;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<Like> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<Favorite> favorite;

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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Favorite> getFavorite() {
        return favorite;
    }

    public void setFavorite(Set<Favorite> favorite) {
        this.favorite = favorite;
    }

}
