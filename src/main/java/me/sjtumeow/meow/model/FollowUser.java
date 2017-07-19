package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class FollowUser extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
	
	@JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private User follower;
	
	@JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private User followee;
	
	public FollowUser() {}
	
	public FollowUser(User follower, User followee) {
		this.follower = follower;
		this.followee = followee;
	}

	public Long getId() {
		return id;
	}
	
	public User getFollower() {
		return follower;
	}

	public User getFollowee() {
		return followee;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public void setFollowee(User followee) {
		this.followee = followee;
	}
	
}
