package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddArticleForm;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.form.AddQuestionForm;
import me.sjtumeow.meow.model.form.UpdateAnswerForm;
import me.sjtumeow.meow.model.form.UpdateArticleForm;
import me.sjtumeow.meow.model.form.UpdateMomentForm;
import me.sjtumeow.meow.model.form.UpdateQuestionForm;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;

public interface ItemService {
	
	// Moment
	
	Iterable<Moment> findAllMoments(String keyword, boolean isAdmin);
	
	Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Moment findMomentById(Long id, boolean isAdmin);
	
	User getMomentCreator(Long id);
	
	CreateResult addMoment(AddMomentForm amf, User user);
	
	boolean updateMoment(Long id, UpdateMomentForm umf);
	
	boolean deleteMoment(Long id);

	
	// Article
	
	Iterable<?> findAllArticles(String keyword, boolean isAdmin);
	
	Iterable<?> findAllArticlesPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Article findArticleById(Long id, boolean isAdmin);
	
	User getArticleCreator(Long id);
	
	Long addArticle(AddArticleForm aaf, User user);
	
	boolean updateArticle(Long id, UpdateArticleForm uaf);
	
	boolean deleteArticle(Long id);
	
	
	// Question
	
	Iterable<Question> findAllQuestions(String keyword, boolean isAdmin);
	
	Iterable<Question> findAllQuestionsPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Question findQuestionById(Long id, boolean isAdmin);
	
	User getQuestionCreator(Long id);
	
	Long addQuestion(AddQuestionForm aqf, User user);
	
	boolean updateQuestion(Long id, UpdateQuestionForm uqf);
	
	boolean deleteQuestion(Long id);
	
	
	// Answer
	
	Iterable<AnswerSummaryResult> findAllAnswers(boolean isAdmin);
	
	Iterable<AnswerSummaryResult> findAllAnswersPageable(Integer page, Integer size, boolean isAdmin);
	
	Answer findAnswerById(Long id, boolean isAdmin);
	
	User getAnswerCreator(Long id);
	
	Long addAnswer(String content, Question question, User user);
	
	boolean updateAnswer(Long id, UpdateAnswerForm uaf);
	
	boolean deleteAnswer(Long id);
	
}
