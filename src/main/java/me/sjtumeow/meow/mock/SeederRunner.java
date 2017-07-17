package me.sjtumeow.meow.mock;

import com.github.javafaker.Faker;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Media.MediaType;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SeederRunner implements ApplicationRunner {
	
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
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private BannerRepository bannerRepository;
    
    @Autowired
    private UserService userService;

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) {
    	
    	Long userId1 = userService.create("13333333333", "meow233", true, "喵喵喵的伙伴", "Web 开发专家", "http://lorempixel.com/50/50");
    	User user1 = userService.findById(userId1, false);
    	Profile profile1 = user1.getProfile();
    	
    	Long userId2 = userService.create("16666666666", "test123", false, "Hacker", "<script>alert('xss!')</script>", null);
    	User user2 = userService.findById(userId2, false);
    	Profile profile2 = user2.getProfile();
        
        for (int i = 0; i < 10; i++) {
        	userService.create(String.format("188%d", 88888001 + i), "111111", false,
        			String.format("吃瓜群众%d", i + 1), i % 2 == 0 ? "不存在的" : null, "http://lorempixel.com/50/50");
        	
            Moment moment = new Moment();
            moment.setProfile(i % 2 == 0 ? profile1 : profile2);
            moment.setContent(faker.shakespeare().hamletQuote());
            momentRepository.save(moment);
            
            if (i == 0) {
                mediaRepository.save(new Media(MediaType.Video, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", moment));
            } else {
            	for (int j = 0; j < i; ++j) {
                    mediaRepository.save(new Media(MediaType.Image, "http://lorempixel.com/200/200", moment));
                }
            }
            
            Comment comment = new Comment(moment, profile1, String.format("神奇评论%d", i + 1));
            commentRepository.save(comment);
        	
        	Article article = new Article();
        	article.setTitle(String.format("铲屎官必读文章(%d)", i + 1));
        	article.setSummary("99% 的铲屎官都不知道的主子特性！删前速看！");
        	article.setContent("<p style=\"color:#63c;\">这是第一段</p><p>这是第二段</p>");
        	article.setCover("http://lorempixel.com/400/200");
        	article.setProfile(i % 2 == 0 ? profile1 : profile2);
        	articleRepository.save(article);
        	
        	Question question = new Question();
        	question.setTitle("四川的猫吃辣吗？");
        	question.setContent("如题，就是想问问在四川领养了一只猫以后，给它秋刀鱼要加辣椒吗？就这个问题...");
        	question.setProfile(i % 2 == 0 ? profile1 : profile2);
        	questionRepository.save(question);
        	
        	Answer answer = new Answer();
        	answer.setContent("<p style=\"color:#63c;\">吃的！吃的！</p><p>但就是哭的样子很难看...</p>");
        	answer.setQuestion(question);
        	answer.setProfile(profile1);
        	answerRepository.save(answer);
        	
        	
        	// Soft delete test
        	
        	if (i == 0) {
        		articleRepository.softDelete(article);
        		questionRepository.softDelete(question);
        		answerRepository.softDelete(answer);
        	}
        	if (i == 3) {
        		momentRepository.softDelete(moment);
        	}
        	
        	
        	// XSS test
        	
        	if (i == 2) {
        		moment.setContent("<script>alert('xss!')</script>");
        		comment.setContent("<script>alert('xss!')</script>");
        		article.setTitle("<script>alert('xss!')</script>");
        		article.setSummary("<script>alert('xss!')</script>");
        		question.setTitle("<script>alert('xss!')</script>");
            	question.setContent("<script>alert('xss!')</script>");
        		momentRepository.save(moment);
        		commentRepository.save(comment);
        		articleRepository.save(article);
        		questionRepository.save(question);
        	}
        	
        	if (i == 1)
        		bannerRepository.save(new Banner(3, "http://lorempixel.com/400/200", moment));
        	else if (i == 2)
        		bannerRepository.save(new Banner(2, "http://lorempixel.com/400/200", article));
        	else if (i == 3)
        		bannerRepository.save(new Banner(1, "http://lorempixel.com/400/200", question));
        	else if (i == 4)
        		bannerRepository.save(new Banner(0, "http://lorempixel.com/400/200", answer));        	
        }
        
    }
    
}
