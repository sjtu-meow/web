package me.sjtumeow.meow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.dao.FavoriteRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Favorite;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Moment;
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
	private FavoriteRepository favoriteRepository;
	
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
}
