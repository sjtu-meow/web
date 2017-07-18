package me.sjtumeow.meow.service;

import java.util.List;

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
import me.sjtumeow.meow.model.result.AnswerDetailResult;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.ArticleDetailResult;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.MomentDetailResult;
import me.sjtumeow.meow.model.result.MomentSummaryResult;
import me.sjtumeow.meow.model.result.QuestionDetailResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.model.util.TimeComparableObject;

public interface ItemService {
	
	// Moment
	
	MomentDetailResult getMomentDetail(Moment moment);
	
	Iterable<MomentSummaryResult> getMomentSummary(Iterable<Moment> moments);
	
	Iterable<?> findAllMoments(String keyword, boolean isAdmin);
	
	Iterable<?> findAllMomentsPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Iterable<MomentSummaryResult> findMomentsByUser(Long userId);
	
	Iterable<MomentSummaryResult> findMomentsByUserPageable(Integer page, Integer size, Long userId);
	
	Moment findMomentById(Long id, boolean isAdmin);
	
	MomentDetailResult showMomentById(Long id);
	
	User getMomentCreator(Long id);
	
	CreateResult addMoment(AddMomentForm amf, User user);
	
	boolean updateMoment(Long id, UpdateMomentForm umf);
	
	boolean deleteMoment(Long id);

	
	// Article
	
	ArticleDetailResult getArticleDetail(Article article);
	
	Iterable<ArticleSummaryResult> getArticleSummary(Iterable<Article> articles);
	
	Iterable<?> findAllArticles(String keyword, boolean isAdmin);
	
	Iterable<?> findAllArticlesPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Iterable<ArticleSummaryResult> findArticlesByUser(Long userId);
	
	Iterable<ArticleSummaryResult> findArticlesByUserPageable(Integer page, Integer size, Long userId);
	
	Article findArticleById(Long id, boolean isAdmin);
	
	ArticleDetailResult showArticleById(Long id);
	
	User getArticleCreator(Long id);
	
	Long addArticle(AddArticleForm aaf, User user);
	
	boolean updateArticle(Long id, UpdateArticleForm uaf);
	
	boolean deleteArticle(Long id);
	
	
	// Question
	
	QuestionDetailResult getQuestionDetail(Question question);
	
	Iterable<QuestionSummaryResult> getQuestionSummary(Iterable<Question> questions);
	
	Iterable<?> findAllQuestions(String keyword, boolean isAdmin);
	
	Iterable<?> findAllQuestionsPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	Iterable<QuestionSummaryResult> findQuestionsByUser(Long userId);
	
	Iterable<QuestionSummaryResult> findQuestionsByUserPageable(Integer page, Integer size, Long userId);
	
	Question findQuestionById(Long id, boolean isAdmin);
	
	QuestionDetailResult showQuestionById(Long id);
	
	User getQuestionCreator(Long id);
	
	Long addQuestion(AddQuestionForm aqf, User user);
	
	boolean updateQuestion(Long id, UpdateQuestionForm uqf);
	
	boolean deleteQuestion(Long id);
	
	
	// Answer
	
	AnswerDetailResult getAnswerDetail(Answer answer);
	
	Iterable<AnswerSummaryResult> getAnswerSummary(Iterable<Answer> answers);
	
	Iterable<?> findAllAnswers(boolean isAdmin);
	
	Iterable<?> findAllAnswersPageable(Integer page, Integer size, boolean isAdmin);
	
	Iterable<AnswerSummaryResult> findAnswersByUser(Long userId);
	
	Iterable<AnswerSummaryResult> findAnswersByUserPageable(Integer page, Integer size, Long userId);
	
	Answer findAnswerById(Long id, boolean isAdmin);
	
	AnswerDetailResult showAnswerById(Long id);
	
	User getAnswerCreator(Long id);
	
	Long addAnswer(String content, Question question, User user);
	
	boolean updateAnswer(Long id, UpdateAnswerForm uaf);
	
	boolean deleteAnswer(Long id);
	
	
	// Comprehensive Search
	
	List<TimeComparableObject> comprehensiveSearch(String keyword);
	
}
