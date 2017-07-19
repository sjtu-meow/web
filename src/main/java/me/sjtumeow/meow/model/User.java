package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isAdmin = false;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Like> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorite;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<FollowQuestion> followingQuestions;

    @JsonIgnore
    @OneToMany(mappedBy = "follower")
    private Set<FollowUser> followees;

    @JsonIgnore
    @OneToMany(mappedBy = "followee")
    private Set<FollowUser> followers;

    public User() {
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public Set<FollowQuestion> getFollowingQuestions() {
        return followingQuestions;
    }

    public void setFollowingQuestions(Set<FollowQuestion> followingQuestions) {
        this.followingQuestions = followingQuestions;
    }

    public Set<FollowUser> getFollowees() {
        return followees;
    }

    public void setFollowees(Set<FollowUser> followees) {
        this.followees = followees;
    }

    public Set<FollowUser> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<FollowUser> followers) {
        this.followers = followers;
    }

}
