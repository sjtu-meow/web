package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddArticleForm;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.form.AddQuestionForm;
import me.sjtumeow.meow.model.form.MediaForm;
import me.sjtumeow.meow.model.form.UpdateAnswerForm;
import me.sjtumeow.meow.model.form.UpdateArticleForm;
import me.sjtumeow.meow.model.form.UpdateMomentForm;
import me.sjtumeow.meow.model.form.UpdateQuestionForm;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	
	@Autowired
    private AnswerRepository answerRepository;
	
	
	// Moment
	
	public Iterable<Moment> findAllMoments(String keyword, boolean isAdmin) {
		return isAdmin ? momentRepository.findByContentContaining(keyword)
				: momentRepository.findByContentContainingAndDeletedAtIsNull(keyword, new Sort(Direction.DESC, "createdAt"));
	}
	
	public Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
		return isAdmin ?
			momentRepository.findByContentContaining(keyword, new PageRequest(page, size))
			: momentRepository.findByContentContainingAndDeletedAtIsNull(keyword, new PageRequest(page, size, Direction.DESC, "createdAt"));
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
		
		if (amf.getMedias() != null) {
			for (MediaForm mf: amf.getMedias()) {
				if (mf.getType() == null || mf.getUrl() == null)
					return new CreateResult();
			}
		}
		
		Moment moment = new Moment();
		moment.setProfile(user.getProfile());
		
		if (content != null)
			moment.setContent(content);
		
		momentRepository.save(moment);
		
		if (amf.getMedias() != null) {
			for (MediaForm mf: amf.getMedias()) {
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
	
	
	// Article
	
	public Iterable<?> findAllArticles(String keyword, boolean isAdmin) {
		if (isAdmin)
			return articleRepository.findByTitleContaining(keyword);
		
		Iterable<Article> articles = articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword, new Sort(Direction.DESC, "createdAt"));
		List<ArticleSummaryResult> result = new ArrayList<ArticleSummaryResult>();
		
		for (Article article: articles) {
			result.add(new ArticleSummaryResult(article));
		}
		
		return result;
	}
	
	public Iterable<?> findAllArticlesPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
		if (isAdmin)
			return articleRepository.findByTitleContaining(keyword, new PageRequest(page, size));
		Iterable<Article> articles = articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword, new PageRequest(page, size, Direction.DESC, "createdAt"));
		List<ArticleSummaryResult> result = new ArrayList<ArticleSummaryResult>();
		
		for (Article article: articles) {
			result.add(new ArticleSummaryResult(article));
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
		Article article = new Article(aaf.getTitle(), aaf.getSummary(), StringUtil.RichTextFilter(aaf.getContent()), aaf.getCover());
		article.setProfile(user.getProfile());
		articleRepository.save(article);
		return article.getId();
	}
	
	@Transactional
	public boolean updateArticle(Long id, UpdateArticleForm uaf) {
		Article article = articleRepository.findOne(id);
		if (article == null)
			return false;
		
		if (uaf.getIsDeleted() != null && !uaf.getIsDeleted())
			article.recover();
		articleRepository.save(article);
		return true;
	}
	
	@Transactional
	public boolean deleteArticle(Long id) {
		if (!articleRepository.existsActive(id))
    		return false;
		articleRepository.softDelete(id);
    	return true;
	}
	
	
	// Question
	
	public Iterable<Question> findAllQuestions(String keyword, boolean isAdmin) {
		return isAdmin ? questionRepository.findByTitleContaining(keyword)
				: questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword, new Sort(Direction.DESC, "createdAt"));
	}
	
	public Iterable<Question> findAllQuestionsPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
		return isAdmin ?
			questionRepository.findByTitleContaining(keyword, new PageRequest(page, size))
			: questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword, new PageRequest(page, size, Direction.DESC, "createdAt"));
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
	public boolean updateQuestion(Long id, UpdateQuestionForm uqf) {
		Question question = questionRepository.findOne(id);
		if (question == null)
			return false;
		
		if (uqf.getIsDeleted() != null && !uqf.getIsDeleted())
			question.recover();
		questionRepository.save(question);
		return true;
	}
	
	@Transactional
	public boolean deleteQuestion(Long id) {
		Question question = questionRepository.findOneActive(id);
		if (question == null)
			return false;
		
		for (Answer answer: question.getAnswers()) {
			answerRepository.softDelete(answer);
		}
		
		questionRepository.softDelete(id);
    	return true;
	}
	
	
	// Answer
	
	public Iterable<AnswerSummaryResult> findAllAnswers(boolean isAdmin) {
		Iterable<Answer> answers = isAdmin ? answerRepository.findAll() : answerRepository.findAllActive(new Sort(Direction.DESC, "createdAt"));
		List<AnswerSummaryResult> result = new ArrayList<AnswerSummaryResult>();
		
		for (Answer answer: answers) {
			result.add(new AnswerSummaryResult(answer));
		}
		
		return result;
	}
	
	public Iterable<AnswerSummaryResult> findAllAnswersPageable(Integer page, Integer size, boolean isAdmin) {
		Iterable<Answer> answers = isAdmin ?
				answerRepository.findAll(new PageRequest(page, size))
				: answerRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
		List<AnswerSummaryResult> result = new ArrayList<AnswerSummaryResult>();
		
		for (Answer answer: answers) {
			result.add(new AnswerSummaryResult(answer));
		}
		
		return result;
	}
	
	public Answer findAnswerById(Long id, boolean isAdmin) {
		return isAdmin ? answerRepository.findOne(id) : answerRepository.findOneActive(id);
	}
	
	public User getAnswerCreator(Long id) {
		Answer answer = answerRepository.findOneActive(id);
		if (answer == null)
			return null;
		return answer.getProfile().getUser();
	}
	
	@Transactional
	public Long addAnswer(String content, Question question, User user) {
		Answer answer = new Answer(StringUtil.RichTextFilter(content));
		answer.setQuestion(question);
		answer.setProfile(user.getProfile());
		answerRepository.save(answer);
		return answer.getId();
	}
	
	@Transactional
	public boolean updateAnswer(Long id, UpdateAnswerForm uaf) {
		Answer answer = answerRepository.findOne(id);
		if (answer == null)
			return false;
		
		if (uaf.getIsDeleted() != null && !uaf.getIsDeleted()) {
			answer.recover();
			answer.getQuestion().recover();
		}
		
		answerRepository.save(answer);
		return true;
	}
	
	@Transactional
	public boolean deleteAnswer(Long id) {
		if (!answerRepository.existsActive(id))
    		return false;
		answerRepository.softDelete(id);
    	return true;
	}
	
	
	// Search
	
	public List<Object> comprehensiveSearch(String keyword) {
		List<Object> result = new ArrayList<Object>();
		
		result.addAll(momentRepository.findByContentContainingAndDeletedAtIsNull(keyword));
		
		for (Article article: articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword)) {
			result.add(new ArticleSummaryResult(article));
		}
		
		result.addAll(questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword));
		
		for (Answer answer: answerRepository.findByContentContainingAndDeletedAtIsNull(keyword)) {
			result.add(new AnswerSummaryResult(answer));
		}
		
		// Sort? Difficult to sort by create time. Or shuffle it?
		
		return result;
	}
}
