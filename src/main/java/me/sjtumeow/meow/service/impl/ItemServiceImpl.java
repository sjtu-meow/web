package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddArticleForm;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.form.AddQuestionForm;
import me.sjtumeow.meow.model.form.MediaForm;
import me.sjtumeow.meow.model.form.UpdateMomentForm;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
    private MomentRepository momentRepository;
	
	@Autowired
    private MediaRepository mediaRepository;
	
	@Autowired
    private ArticleRepository articleRepository;
	
	@Autowired
    private QuestionRepository questionRepository;
	
	
	public Iterable<Moment> findAllMoments(boolean isAdmin) {
		return isAdmin ? momentRepository.findAll() : momentRepository.findAllActive();
	}
	
	public Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, boolean isAdmin) {
		return isAdmin ?
			momentRepository.findAll(new PageRequest(page, size, Direction.DESC, "createdAt")) 
			: momentRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
	}
    
	public Moment findMomentById(Long id, boolean isAdmin) {
		return isAdmin ? momentRepository.findOne(id) : momentRepository.findOneActive(id);
	}
	
	public User getMomentCreator(Long id) {
		Moment moment = momentRepository.findOneActive(id);
		if (moment == null)
			return null;
		return moment.getProfile().getUser();
	}
	
	@Transactional
	public CreateResult addMoment(AddMomentForm amf, User user) {
		String content = amf.getContent();
		if ((content == null || content.trim().isEmpty()) && (amf.getMedias() == null || amf.getMedias().isEmpty()))
			return new CreateResult();
		
		Moment moment = new Moment();
		moment.setProfile(user.getProfile());
		
		if (content != null)
			moment.setContent(content);
		
		momentRepository.save(moment);
		
		if (amf.getMedias() != null) {
			for (MediaForm mf: amf.getMedias()) {
				if (mf.getType() == null || mf.getUrl() == null)
					return new CreateResult();
				mediaRepository.save(new Media(mf.getType(), mf.getUrl(), moment));
			}
		}
		
		return new CreateResult(moment.getId());
	}
	
	@Transactional
	public boolean updateMoment(Long id, UpdateMomentForm umf) {
		Moment moment = momentRepository.findOne(id);
		if (moment == null)
			return false;
		
		if (umf.getIsDeleted() != null && !umf.getIsDeleted())
			moment.recover();
		momentRepository.save(moment);
		return true;
	}
	
	@Transactional
	public boolean deleteMoment(Long id) {
		if (!momentRepository.existsActive(id))
    		return false;
    	momentRepository.softDelete(id);
    	return true;
	}
	
	public Iterable<ArticleSummaryResult> findAllArticles(boolean isAdmin) {
		Iterable<Article> articles = isAdmin ? articleRepository.findAll() : articleRepository.findAllActive();
		List<ArticleSummaryResult> result = new ArrayList<ArticleSummaryResult>();
		
		for (Article article: articles) {
			ArticleSummaryResult asr = new ArticleSummaryResult();
			asr.setId(article.getId());
			asr.setTitle(article.getTitle());
			asr.setSummary(article.getSummary());
			asr.setCover(article.getCover());
			asr.setProfile(article.getProfile());
			asr.setLikeCount(article.getLikeCount());
			asr.setCommentCount(article.getCommentCount());
			asr.setCreateTime(article.getCreateTime());
			asr.setUpdateTime(article.getUpdateTime());
			asr.setDeleted(article.isDeleted());
			result.add(asr);
		}
		
		return result;
	}
	
	public Iterable<ArticleSummaryResult> findAllArticlesPageable(Integer page, Integer size, boolean isAdmin) {
		Iterable<Article> articles = isAdmin ?
				articleRepository.findAll(new PageRequest(page, size, Direction.DESC, "createdAt")) 
				: articleRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
		List<ArticleSummaryResult> result = new ArrayList<ArticleSummaryResult>();
		
		for (Article article: articles) {
			ArticleSummaryResult asr = new ArticleSummaryResult();
			asr.setId(article.getId());
			asr.setTitle(article.getTitle());
			asr.setSummary(article.getSummary());
			asr.setCover(article.getCover());
			asr.setProfile(article.getProfile());
			asr.setLikeCount(article.getLikeCount());
			asr.setCommentCount(article.getCommentCount());
			asr.setCreateTime(article.getCreateTime());
			asr.setUpdateTime(article.getUpdateTime());
			asr.setDeleted(article.isDeleted());
			result.add(asr);
		}
		
		return result;
	}
	
	public Article findArticleById(Long id, boolean isAdmin) {
		return isAdmin ? articleRepository.findOne(id) : articleRepository.findOneActive(id);
	}
	
	public User getArticleCreator(Long id) {
		Article article = articleRepository.findOneActive(id);
		if (article == null)
			return null;
		return article.getProfile().getUser();
	}
	
	@Transactional
	public Long addArticle(AddArticleForm aaf, User user) {
		Article article = new Article(aaf.getTitle(), aaf.getSummary(), aaf.getContent(), aaf.getCover());
		article.setProfile(user.getProfile());
		articleRepository.save(article);
		return article.getId();
	}
	
	@Transactional
	public boolean deleteArticle(Long id) {
		if (!articleRepository.existsActive(id))
    		return false;
		articleRepository.softDelete(id);
    	return true;
	}
	
	public Iterable<QuestionSummaryResult> findAllQuestions(boolean isAdmin) {
		Iterable<Question> questions = isAdmin ? questionRepository.findAll() : questionRepository.findAllActive();
		List<QuestionSummaryResult> result = new ArrayList<QuestionSummaryResult>();
		
		for (Question question: questions) {
			QuestionSummaryResult qsr = new QuestionSummaryResult();
			qsr.setId(question.getId());
			qsr.setTitle(question.getTitle());
			qsr.setContent(question.getContent());
			qsr.setProfile(question.getProfile());
			qsr.setCreateTime(question.getCreateTime());
			qsr.setUpdateTime(question.getUpdateTime());
		    qsr.setDeleted(question.isDeleted());
		    result.add(qsr);
		}
		
		return result;
	}
	
	public Iterable<QuestionSummaryResult> findAllQuestionsPageable(Integer page, Integer size, boolean isAdmin) {
		Iterable<Question> questions = isAdmin ?
				questionRepository.findAll(new PageRequest(page, size, Direction.DESC, "createdAt")) 
				: questionRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
		List<QuestionSummaryResult> result = new ArrayList<QuestionSummaryResult>();
		
		for (Question question: questions) {
			QuestionSummaryResult qsr = new QuestionSummaryResult();
			qsr.setId(question.getId());
			qsr.setTitle(question.getTitle());
			qsr.setContent(question.getContent());
			qsr.setProfile(question.getProfile());
			qsr.setCreateTime(question.getCreateTime());
			qsr.setUpdateTime(question.getUpdateTime());
		    qsr.setDeleted(question.isDeleted());
		    result.add(qsr);
		}
		
		return result;
	}
	
	public Question findQuestionById(Long id, boolean isAdmin) {
		return isAdmin ? questionRepository.findOne(id) : questionRepository.findOneActive(id);
	}
	
	public User getQuestionCreator(Long id) {
		Question question = questionRepository.findOneActive(id);
		if (question == null)
			return null;
		return question.getProfile().getUser();
	}
	
	@Transactional
	public Long addQuestion(AddQuestionForm aqf, User user) {
		Question question = new Question(aqf.getTitle(), aqf.getContent());
		question.setProfile(user.getProfile());
		questionRepository.save(question);
		return question.getId();
	}
	
	@Transactional
	public boolean deleteQuestion(Long id) {
		if (!questionRepository.existsActive(id))
    		return false;
		questionRepository.softDelete(id);
    	return true;
	}
}
