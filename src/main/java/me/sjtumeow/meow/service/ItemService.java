package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.form.UpdateMomentForm;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;

public interface ItemService {
	
	// Moment
	
	Iterable<Moment> findAllMoments(boolean isAdmin);
	
	Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, boolean isAdmin);
	
	Moment findMomentById(Long id, boolean isAdmin);
	
	User getMomentCreator(Long id);
	
	CreateResult addMoment(AddMomentForm amf, User user);
	
	boolean updateMoment(Long id, UpdateMomentForm umf);
	
	boolean deleteMoment(Long id);

	
	// Article
	
	Iterable<ArticleSummaryResult> findAllArticles(boolean isAdmin);
	
	Iterable<ArticleSummaryResult> findAllArticlesPageable(Integer page, Integer size, boolean isAdmin);
	
	Article findArticleById(Long id, boolean isAdmin);
	
	User getArticleCreator(Long id);
	
	
	// Question
	
	Iterable<QuestionSummaryResult> findAllQuestions(boolean isAdmin);
	
	Iterable<QuestionSummaryResult> findAllQuestionsPageable(Integer page, Integer size, boolean isAdmin);
	
	Question findQuestionById(Long id, boolean isAdmin);
	
}
