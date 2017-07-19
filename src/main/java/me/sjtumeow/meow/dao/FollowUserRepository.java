package me.sjtumeow.meow.dao;

import java.util.List;

import me.sjtumeow.meow.model.FollowUser;
import me.sjtumeow.meow.model.User;

public interface FollowUserRepository extends SoftDeleteRepository<FollowUser, Long> {
	
	List<FollowUser> findByFollowerAndFollowee(User follower, User followee);
	
	void deleteByFollowerAndFollowee(User follower, User followee);
}
