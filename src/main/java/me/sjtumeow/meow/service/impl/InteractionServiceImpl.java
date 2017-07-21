package me.sjtumeow.meow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.FavoriteRepository;
import me.sjtumeow.meow.dao.FollowQuestionRepository;
import me.sjtumeow.meow.dao.FollowUserRepository;
import me.sjtumeow.meow.dao.LikeRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.dao.ReportRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Favorite;
import me.sjtumeow.meow.model.FollowQuestion;
import me.sjtumeow.meow.model.FollowUser;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Like;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.Report;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ReportForm;
import me.sjtumeow.meow.model.form.UpdateCommentForm;
import me.sjtumeow.meow.model.form.UpdateReportForm;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.MomentSummaryResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private MomentRepository momentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FollowQuestionRepository followQuestionRepository;

    @Autowired
    private FollowUserRepository followUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportRepository reportRepository;

    // Like

    public boolean checkLike(User user, Item item) {
        List<Like> result = likeRepository.findByUserAndItem(user, item);
        return result != null && !result.isEmpty();
    }

    @Transactional
    public boolean doLike(User user, Item item) {
        if (checkLike(user, item))
            return false;
        likeRepository.save(new Like(user, item));
        return true;
    }

    @Transactional
    public void cancelLike(User user, Item item) {
        likeRepository.deleteByUserAndItem(user, item);
    }

    // Favorite

    public List<BaseSummaryResult> getUserFavorites(User user) {
        List<BaseSummaryResult> result = new ArrayList<BaseSummaryResult>();
        List<Favorite> favorites = new ArrayList<Favorite>();

        for (Favorite favorite : user.getFavorite()) {
            if (!favorite.getItem().isDeleted())
                favorites.add(favorite);
        }

        Collections.sort(favorites, new Comparator<Favorite>() {
            @Override
            public int compare(Favorite lhs, Favorite rhs) {
                Integer res = lhs.getCreatedAt().compareTo(rhs.getCreatedAt());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });

        for (Favorite favorite : favorites) {
            Item item = favorite.getItem();
            switch (item.getType()) {
                case Item.ITEM_TYPE_MOMENT:
                    result.add(new MomentSummaryResult((Moment) item));
                    break;
                case Item.ITEM_TYPE_ARTICLE:
                    result.add(new ArticleSummaryResult((Article) item));
                    break;
                case Item.ITEM_TYPE_QUESTION:
                    result.add(new QuestionSummaryResult((Question) item));
                    break;
                case Item.ITEM_TYPE_ANSWER:
                    result.add(new AnswerSummaryResult((Answer) item));
                    break;
            }
        }

        return result;
    }

    public boolean checkFavorite(User user, Item item) {
        List<Favorite> result = favoriteRepository.findByUserAndItem(user, item);
        return result != null && !result.isEmpty();
    }

    @Transactional
    public boolean doFavorite(User user, Item item) {
        if (checkFavorite(user, item))
            return false;
        favoriteRepository.save(new Favorite(user, item));
        return true;
    }

    @Transactional
    public void cancelFavorite(User user, Item item) {
        favoriteRepository.deleteByUserAndItem(user, item);
    }

    // Follow

    public List<QuestionSummaryResult> getUserFollowingQuestions(User user) {
        List<QuestionSummaryResult> result = new ArrayList<QuestionSummaryResult>();
        List<FollowQuestion> following = new ArrayList<FollowQuestion>();

        for (FollowQuestion fq : user.getFollowingQuestions()) {
            if (!fq.getQuestion().isDeleted())
                following.add(fq);
        }

        Collections.sort(following, new Comparator<FollowQuestion>() {
            @Override
            public int compare(FollowQuestion lhs, FollowQuestion rhs) {
                Integer res = lhs.getCreatedAt().compareTo(rhs.getCreatedAt());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });

        for (FollowQuestion fq : following) {
            result.add(new QuestionSummaryResult(fq.getQuestion()));
        }

        return result;
    }

    public boolean checkFollowQuestion(User user, Question question) {
        List<FollowQuestion> result = followQuestionRepository.findByUserAndQuestion(user, question);
        return result != null && !result.isEmpty();
    }

    @Transactional
    public boolean doFollowQuestion(User user, Question question) {
        if (checkFollowQuestion(user, question))
            return false;
        followQuestionRepository.save(new FollowQuestion(user, question));
        return true;
    }

    @Transactional
    public void cancelFollowQuestion(User user, Question question) {
        followQuestionRepository.deleteByUserAndQuestion(user, question);
    }

    public List<Profile> getUserFollowees(User user) {
        List<Profile> result = new ArrayList<Profile>();
        List<FollowUser> followees = new ArrayList<FollowUser>();

        for (FollowUser followee : user.getFollowees()) {
            if (!followee.getFollowee().isDeleted())
                followees.add(followee);
        }

        Collections.sort(followees, new Comparator<FollowUser>() {
            @Override
            public int compare(FollowUser lhs, FollowUser rhs) {
                Integer res = lhs.getCreatedAt().compareTo(rhs.getCreatedAt());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });

        for (FollowUser followee : followees) {
            result.add(followee.getFollowee().getProfile());
        }

        return result;
    }

    public boolean checkFollowUser(User follower, User followee) {
        List<FollowUser> result = followUserRepository.findByFollowerAndFollowee(follower, followee);
        return result != null && !result.isEmpty();
    }

    @Transactional
    public boolean doFollowUser(User follower, User followee) {
        if (checkFollowUser(follower, followee))
            return false;
        followUserRepository.save(new FollowUser(follower, followee));
        return true;
    }

    @Transactional
    public void cancelFollowUser(User follower, User followee) {
        followUserRepository.deleteByFollowerAndFollowee(follower, followee);
    }

    // Comment

    public Iterable<Comment> findAllComments(String keyword) {
        return commentRepository.findByContentContaining(keyword);
    }

    public Iterable<Comment> findAllCommentsPageable(Integer page, Integer size, String keyword) {
        return commentRepository.findByContentContaining(keyword, new PageRequest(page, size));
    }

    public Comment findCommentById(Long id, boolean isAdmin) {
        return isAdmin ? commentRepository.findOne(id) : commentRepository.findOneActive(id);
    }

    public User getCommentCreator(Long id) {
        Comment comment = commentRepository.findOneActive(id);
        return comment == null ? null : comment.getProfile().getUser();
    }

    @Transactional
    public Long addComment(Item item, User user, String content) {
        Comment comment = new Comment(item, user.getProfile(), content);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public boolean updateComment(Long id, UpdateCommentForm ucf) {
        Comment comment = commentRepository.findOne(id);
        if (comment == null)
            return false;

        if (ucf.getIsDeleted() != null && !ucf.getIsDeleted())
            comment.recover();
        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public boolean deleteComment(Long id) {
        if (!commentRepository.existsActive(id))
            return false;
        commentRepository.softDelete(id);
        return true;
    }

    // Report

    public Iterable<Report> findAllReports(Integer type, String status) {
        Boolean closed = StringUtil.parseReportStatus(status);
        return closed == null ? reportRepository.findByItemType(type, new Sort(Direction.DESC, "createdAt"))
                : reportRepository.findByItemTypeAndClosed(type, closed, new Sort(Direction.DESC, "createdAt"));
    }

    public Iterable<Report> findAllReportsPageable(Integer page, Integer size, Integer type, String status) {
        Boolean closed = StringUtil.parseReportStatus(status);
        return closed == null
                ? reportRepository.findByItemType(type, new PageRequest(page, size, Direction.DESC, "createdAt"))
                : reportRepository.findByItemTypeAndClosed(type, closed,
                        new PageRequest(page, size, Direction.DESC, "createdAt"));
    }

    public Report findReportById(Long id) {
        return reportRepository.findOne(id);
    }

    @Transactional
    public Long doReport(Item item, User user, String reason) {
        Report report = new Report(item, user.getProfile(), reason);
        reportRepository.save(report);
        return report.getId();
    }

    @Transactional
    public CreateResult doReport(ReportForm rf, User user) {
        Long itemId = rf.getItemId();
        if (itemId == null)
            return new CreateResult("缺失项目编号");

        Integer itemType = rf.getItemType();
        if (!FormatValidator.checkItemType(itemType))
            return new CreateResult("非法的项目类型");

        Item item;

        switch (itemType) {
            case Item.ITEM_TYPE_MOMENT:
                item = momentRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的点滴不存在");
                break;
            case Item.ITEM_TYPE_ARTICLE:
                item = articleRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的文章不存在");
                break;
            case Item.ITEM_TYPE_QUESTION:
                item = questionRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的问题不存在");
                break;
            case Item.ITEM_TYPE_ANSWER:
                item = answerRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的回答不存在");
                break;
            default:
                item = commentRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的评论不存在");
                break;
        }

        Report report = new Report(item, user.getProfile(), StringUtil.replaceNull(rf.getReason()));
        reportRepository.save(report);
        return new CreateResult(report.getId());
    }

    @Transactional
    public boolean updateReport(Long id, UpdateReportForm urf) {
        Report report = reportRepository.findOne(id);
        if (report == null)
            return false;

        Boolean closed = urf.getClosed();
        if (closed != null)
            report.setClosed(closed);
        reportRepository.save(report);
        return true;
    }

}
