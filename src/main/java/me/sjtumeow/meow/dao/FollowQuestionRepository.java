package me.sjtumeow.meow.dao;

import java.util.List;

import me.sjtumeow.meow.model.FollowQuestion;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;

public interface FollowQuestionRepository extends SoftDeleteRepository<FollowQuestion, Long> {
	
	List<FollowQuestion> findByUserAndQuestion(User user, Question question);
	
	void deleteByUserAndQuestion(User user, Question question);
}
