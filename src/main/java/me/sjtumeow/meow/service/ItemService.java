package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;

public interface ItemService {
	
	// Moment
	
	Iterable<Moment> findAllMoments(boolean isAdmin);
	
	Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, boolean isAdmin);
	
	Moment findMomentById(Long id, boolean isAdmin);
	
	User getMomentCreator(Long id);
	
	boolean addMoment(AddMomentForm amf, User user);
	
	boolean deleteMoment(Long id);
	
	boolean recoverMoment(Long id);
	
	
	// Article
	
	Iterable<ArticleSummaryResult> findAllArticles(boolean isAdmin);
	
	Iterable<ArticleSummaryResult> findAllArticlesPageable(Integer page, Integer size, boolean isAdmin);
	
	Article findArticleById(Long id, boolean isAdmin);
	
	User getArticleCreator(Long id);
	
}
