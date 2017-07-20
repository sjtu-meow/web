package me.sjtumeow.meow.service;

import java.util.List;

import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;

public interface InteractionService {

    // Like

    boolean checkLike(User user, Item item);

    boolean doLike(User user, Item item);

    void cancelLike(User user, Item item);

    // Favorite

    List<BaseSummaryResult> getUserFavorites(User user);

    boolean checkFavorite(User user, Item item);

    boolean doFavorite(User user, Item item);

    void cancelFavorite(User user, Item item);

    // Follow

    List<QuestionSummaryResult> getUserFollowingQuestions(User user);

    boolean checkFollowQuestion(User user, Question question);

    boolean doFollowQuestion(User user, Question question);

    void cancelFollowQuestion(User user, Question question);

    List<Profile> getUserFollowees(User user);

    boolean checkFollowUser(User follower, User followee);

    boolean doFollowUser(User follower, User followee);

    void cancelFollowUser(User follower, User followee);

    // Comment

    User getCommentCreator(Long id);

    Long addComment(Item item, User user, String content);

    boolean deleteComment(Long id);
}
