package me.sjtumeow.meow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.dao.FavoriteRepository;
import me.sjtumeow.meow.dao.FollowQuestionRepository;
import me.sjtumeow.meow.dao.FollowUserRepository;
import me.sjtumeow.meow.dao.LikeRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Favorite;
import me.sjtumeow.meow.model.FollowQuestion;
import me.sjtumeow.meow.model.FollowUser;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Like;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.MomentSummaryResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.InteractionService;

@Service
public class InteractionServiceImpl implements InteractionService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private FollowQuestionRepository followQuestionRepository;
	
	@Autowired
	private FollowUserRepository followUserRepository;
	
	
	// Like
	
	public boolean checkLike(User user, Item item) {
		List<Like> result = likeRepository.findByUserAndItem(user, item);
		return result != null && !result.isEmpty();
	}
	
	@Transactional
	public void doLike(User user, Item item) {
		if (checkLike(user, item))
			return;
		likeRepository.save(new Like(user, item));
	}
	
	@Transactional
	public void cancelLike(User user, Item item) {
		likeRepository.deleteByUserAndItem(user, item);
	}
	
	
	// Favorite
	
	public List<BaseSummaryResult> getUserFavorites(User user) {
		List<BaseSummaryResult> result = new ArrayList<BaseSummaryResult>();
		List<Favorite> favorites = new ArrayList<Favorite>();
		
		for (Favorite favorite: user.getFavorite()) {
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
		
		for (Favorite favorite: favorites) {
			Item item = favorite.getItem();
			switch (item.getType()) {
				case Item.ITEM_TYPE_MOMENT:
					result.add(new MomentSummaryResult((Moment)item));
					break;
				case Item.ITEM_TYPE_ARTICLE:
					result.add(new ArticleSummaryResult((Article)item));
					break;
				case Item.ITEM_TYPE_QUESTION:
					result.add(new QuestionSummaryResult((Question)item));
					break;
				case Item.ITEM_TYPE_ANSWER:
					result.add(new AnswerSummaryResult((Answer)item));
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
	public void doFavorite(User user, Item item) {
		if (checkFavorite(user, item))
			return;
		favoriteRepository.save(new Favorite(user, item));
	}
	
	@Transactional
	public void cancelFavorite(User user, Item item) {
		favoriteRepository.deleteByUserAndItem(user, item);
	}
	
	
	// Follow
	
	public List<QuestionSummaryResult> getUserFollowingQuestions(User user) {
		List<QuestionSummaryResult> result = new ArrayList<QuestionSummaryResult>();
		List<FollowQuestion> following = new ArrayList<FollowQuestion>();
		
		for (FollowQuestion fq: user.getFollowingQuestions()) {
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
		
		for (FollowQuestion fq: following) {
			result.add(new QuestionSummaryResult(fq.getQuestion()));	
		}
		
		return result;
	}
	
	public boolean checkFollowQuestion(User user, Question question) {
		List<FollowQuestion> result = followQuestionRepository.findByUserAndQuestion(user, question);
		return result != null && !result.isEmpty();
	}
	
	@Transactional
	public void doFollowQuestion(User user, Question question) {
		if (checkFollowQuestion(user, question))
			return;
		followQuestionRepository.save(new FollowQuestion(user, question));
	}
	
	@Transactional
	public void cancelFollowQuestion(User user, Question question) {
		followQuestionRepository.deleteByUserAndQuestion(user, question);
	}
	
	public List<Profile> getUserFollowees(User user) {
		List<Profile> result = new ArrayList<Profile>();
		List<FollowUser> followees = new ArrayList<FollowUser>();
		
		for (FollowUser followee: user.getFollowees()) {
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
		
		for (FollowUser followee: followees) {
			result.add(followee.getFollowee().getProfile());	
		}
		
		return result;
	}
	
	public boolean checkFollowUser(User follower, User followee) {
		List<FollowUser> result = followUserRepository.findByFollowerAndFollowee(follower, followee);
		return result != null && !result.isEmpty();
	}
	
	@Transactional
	public void doFollowUser(User follower, User followee) {
		if (checkFollowUser(follower, followee))
			return;
		followUserRepository.save(new FollowUser(follower, followee));
	}
	
	@Transactional
	public void cancelFollowUser(User follower, User followee) {
		followUserRepository.deleteByFollowerAndFollowee(follower, followee);
	}
	
}
