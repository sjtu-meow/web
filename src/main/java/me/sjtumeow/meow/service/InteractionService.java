package me.sjtumeow.meow.service;

import java.util.List;

import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.Report;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ReportForm;
import me.sjtumeow.meow.model.form.UpdateCommentForm;
import me.sjtumeow.meow.model.form.UpdateReportForm;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
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

    Iterable<Comment> findAllComments(String keyword);

    Iterable<Comment> findAllCommentsPageable(Integer page, Integer size, String keyword);

    Comment findCommentById(Long id, boolean isAdmin);

    User getCommentCreator(Long id);

    Long addComment(Item item, User user, String content);

    boolean updateComment(Long id, UpdateCommentForm ucf);

    boolean deleteComment(Long id);

    // Report

    Iterable<Report> findAllReports(Integer type, String status);

    Iterable<Report> findAllReportsPageable(Integer page, Integer size, Integer type, String status);

    Report findReportById(Long id);

    Long doReport(Item item, User user, String reason);

    CreateResult doReport(ReportForm rf, User user);

    boolean updateReport(Long id, UpdateReportForm urf);
}
