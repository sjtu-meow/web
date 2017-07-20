package me.sjtumeow.meow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import me.sjtumeow.meow.model.result.AnswerDetailResult;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.ArticleDetailResult;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.MomentDetailResult;
import me.sjtumeow.meow.model.result.MomentSummaryResult;
import me.sjtumeow.meow.model.result.QuestionDetailResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.StringUtil;

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

    public MomentDetailResult getMomentDetail(Moment moment) {
        return moment == null ? null : new MomentDetailResult(moment);
    }

    public Iterable<MomentSummaryResult> getMomentSummary(Iterable<Moment> moments) {
        List<MomentSummaryResult> result = new ArrayList<MomentSummaryResult>();

        for (Moment moment : moments) {
            result.add(new MomentSummaryResult(moment));
        }

        return result;
    }

    public Iterable<?> findAllMoments(String keyword, boolean isAdmin) {
        return isAdmin ? momentRepository.findByContentContaining(keyword)
                : getMomentSummary(momentRepository.findByContentContainingAndDeletedAtIsNull(keyword,
                        new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<?> findAllMomentsPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
        return isAdmin ? momentRepository.findByContentContaining(keyword, new PageRequest(page, size))
                : getMomentSummary(momentRepository.findByContentContainingAndDeletedAtIsNull(keyword,
                        new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Iterable<MomentSummaryResult> findMomentsByUser(Long userId) {
        return getMomentSummary(
                momentRepository.findByProfileIdAndDeletedAtIsNull(userId, new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<MomentSummaryResult> findMomentsByUserPageable(Integer page, Integer size, Long userId) {
        return getMomentSummary(momentRepository.findByProfileIdAndDeletedAtIsNull(userId,
                new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Moment findMomentById(Long id, boolean isAdmin) {
        return isAdmin ? momentRepository.findOne(id) : momentRepository.findOneActive(id);
    }

    public MomentDetailResult showMomentById(Long id) {
        return getMomentDetail(momentRepository.findOneActive(id));
    }

    public User getMomentCreator(Long id) {
        Moment moment = momentRepository.findOneActive(id);
        return moment == null ? null : moment.getProfile().getUser();
    }

    @Transactional
    public CreateResult addMoment(AddMomentForm amf, User user) {
        String content = amf.getContent();
        if ((content == null || content.trim().isEmpty()) && (amf.getMedias() == null || amf.getMedias().isEmpty()))
            return new CreateResult();

        if (amf.getMedias() != null) {
            for (MediaForm mf : amf.getMedias()) {
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
            for (MediaForm mf : amf.getMedias()) {
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

    public ArticleDetailResult getArticleDetail(Article article) {
        return article == null ? null : new ArticleDetailResult(article);
    }

    public Iterable<ArticleSummaryResult> getArticleSummary(Iterable<Article> articles) {
        List<ArticleSummaryResult> result = new ArrayList<ArticleSummaryResult>();

        for (Article article : articles) {
            result.add(new ArticleSummaryResult(article));
        }

        return result;
    }

    public Iterable<?> findAllArticles(String keyword, boolean isAdmin) {
        return isAdmin ? articleRepository.findByTitleContaining(keyword)
                : getArticleSummary(articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword,
                        new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<?> findAllArticlesPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
        return isAdmin ? articleRepository.findByTitleContaining(keyword, new PageRequest(page, size))
                : getArticleSummary(articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword,
                        new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Iterable<ArticleSummaryResult> findArticlesByUser(Long userId) {
        return getArticleSummary(
                articleRepository.findByProfileIdAndDeletedAtIsNull(userId, new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<ArticleSummaryResult> findArticlesByUserPageable(Integer page, Integer size, Long userId) {
        return getArticleSummary(articleRepository.findByProfileIdAndDeletedAtIsNull(userId,
                new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Article findArticleById(Long id, boolean isAdmin) {
        return isAdmin ? articleRepository.findOne(id) : articleRepository.findOneActive(id);
    }

    public ArticleDetailResult showArticleById(Long id) {
        return getArticleDetail(articleRepository.findOneActive(id));
    }

    public User getArticleCreator(Long id) {
        Article article = articleRepository.findOneActive(id);
        return article == null ? null : article.getProfile().getUser();
    }

    @Transactional
    public Long addArticle(AddArticleForm aaf, User user) {
        Article article = new Article(aaf.getTitle(), aaf.getSummary(), StringUtil.filterRichText(aaf.getContent()),
                aaf.getCover());
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

    public QuestionDetailResult getQuestionDetail(Question question) {
        return question == null ? null : new QuestionDetailResult(question);
    }

    public Iterable<QuestionSummaryResult> getQuestionSummary(Iterable<Question> questions) {
        List<QuestionSummaryResult> result = new ArrayList<QuestionSummaryResult>();

        for (Question question : questions) {
            result.add(new QuestionSummaryResult(question));
        }

        return result;
    }

    public Iterable<?> findAllQuestions(String keyword, boolean isAdmin) {
        return isAdmin ? questionRepository.findByTitleContaining(keyword)
                : getQuestionSummary(questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword,
                        new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<?> findAllQuestionsPageable(Integer page, Integer size, String keyword, boolean isAdmin) {
        return isAdmin ? questionRepository.findByTitleContaining(keyword, new PageRequest(page, size))
                : getQuestionSummary(questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword,
                        new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Iterable<QuestionSummaryResult> findQuestionsByUser(Long userId) {
        return getQuestionSummary(
                questionRepository.findByProfileIdAndDeletedAtIsNull(userId, new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<QuestionSummaryResult> findQuestionsByUserPageable(Integer page, Integer size, Long userId) {
        return getQuestionSummary(questionRepository.findByProfileIdAndDeletedAtIsNull(userId,
                new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Question findQuestionById(Long id, boolean isAdmin) {
        return isAdmin ? questionRepository.findOne(id) : questionRepository.findOneActive(id);
    }

    public QuestionDetailResult showQuestionById(Long id) {
        return getQuestionDetail(questionRepository.findOneActive(id));
    }

    public User getQuestionCreator(Long id) {
        Question question = questionRepository.findOneActive(id);
        return question == null ? null : question.getProfile().getUser();
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

        for (Answer answer : question.getAnswers()) {
            answerRepository.softDelete(answer);
        }

        questionRepository.softDelete(id);
        return true;
    }

    // Answer

    public AnswerDetailResult getAnswerDetail(Answer answer) {
        return answer == null ? null : new AnswerDetailResult(answer);
    }

    public Iterable<AnswerSummaryResult> getAnswerSummary(Iterable<Answer> answers) {
        List<AnswerSummaryResult> result = new ArrayList<AnswerSummaryResult>();

        for (Answer answer : answers) {
            result.add(new AnswerSummaryResult(answer));
        }

        return result;
    }

    public Iterable<?> findAllAnswers(boolean isAdmin) {
        return isAdmin ? answerRepository.findAll()
                : getAnswerSummary(answerRepository.findAllActive(new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<?> findAllAnswersPageable(Integer page, Integer size, boolean isAdmin) {
        return isAdmin ? answerRepository.findAll(new PageRequest(page, size))
                : getAnswerSummary(
                        answerRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Iterable<AnswerSummaryResult> findAnswersByUser(Long userId) {
        return getAnswerSummary(
                answerRepository.findByProfileIdAndDeletedAtIsNull(userId, new Sort(Direction.DESC, "createdAt")));
    }

    public Iterable<AnswerSummaryResult> findAnswersByUserPageable(Integer page, Integer size, Long userId) {
        return getAnswerSummary(answerRepository.findByProfileIdAndDeletedAtIsNull(userId,
                new PageRequest(page, size, Direction.DESC, "createdAt")));
    }

    public Answer findAnswerById(Long id, boolean isAdmin) {
        return isAdmin ? answerRepository.findOne(id) : answerRepository.findOneActive(id);
    }

    public AnswerDetailResult showAnswerById(Long id) {
        return getAnswerDetail(answerRepository.findOneActive(id));
    }

    public User getAnswerCreator(Long id) {
        Answer answer = answerRepository.findOneActive(id);
        return answer == null ? null : answer.getProfile().getUser();
    }

    @Transactional
    public Long addAnswer(String content, Question question, User user) {
        Answer answer = new Answer(StringUtil.filterRichText(content));
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

    // Comprehensive Search

    public List<BaseSummaryResult> comprehensiveSearch(String keyword) {
        List<BaseSummaryResult> result = new ArrayList<BaseSummaryResult>();

        for (Moment moment : momentRepository.findByContentContainingAndDeletedAtIsNull(keyword)) {
            result.add(new MomentSummaryResult(moment));
        }

        for (Article article : articleRepository.findByTitleContainingAndDeletedAtIsNull(keyword)) {
            result.add(new ArticleSummaryResult(article));
        }

        for (Question question : questionRepository.findByTitleContainingAndDeletedAtIsNull(keyword)) {
            result.add(new QuestionSummaryResult(question));
        }

        for (Answer answer : answerRepository.findByContentContainingAndDeletedAtIsNull(keyword)) {
            result.add(new AnswerSummaryResult(answer));
        }

        Collections.sort(result, new Comparator<BaseSummaryResult>() {
            @Override
            public int compare(BaseSummaryResult lhs, BaseSummaryResult rhs) {
                Integer res = lhs.getCreateTime().compareTo(rhs.getCreateTime());
                return res == 0 ? 0 : -res / Math.abs(res);
            }
        });

        return result;
    }
}
