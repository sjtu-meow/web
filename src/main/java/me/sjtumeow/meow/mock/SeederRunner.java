package me.sjtumeow.meow.mock;

import com.github.javafaker.Faker;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.dao.UserRepository;
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

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SeederRunner implements ApplicationRunner {
    @Autowired MomentRepository momentRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired UserRepository userRepository;
    @Autowired ProfileRepository profileRepository;
    @Autowired MediaRepository mediaRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired BannerRepository bannerRepository;
    @Autowired QuestionRepository questionRepository;
    @Autowired AnswerRepository answerRepository;

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) {
       
    	User user1 = new User("12312312312", BCrypt.hashpw("meow233", BCrypt.gensalt()));
    	User user2 = new User("12132132132", BCrypt.hashpw("test123", BCrypt.gensalt()));
    	user1.setAdmin(true);
    	userRepository.save(user1);
        userRepository.save(user2);
        
        Profile profile1 = new Profile();
        profile1.setNickname("喵喵喵的伙伴");
        profile1.setBio("Web 开发专家");
        profile1.setUser(user1);
        profileRepository.save(profile1);
        
        Profile profile2 = new Profile();
        profile2.setNickname("啦啦啦");
        profile2.setBio("<script>alert('xss!')</script>");
        profile2.setUser(user2);
        profileRepository.save(profile2);
        
        for (int i = 0; i < 10; i++) {
            Moment moment = new Moment();
            moment.setProfile(i % 2 == 0 ? profile1 : profile2);
            moment.setContent(faker.shakespeare().hamletQuote());
            momentRepository.save(moment);
            
            if (i == 0) {
            	Media media = new Media(MediaType.Video, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", moment);
                mediaRepository.save(media);
            } else {
            	for (int j = 0; j < i; ++j) {
                	Media media = new Media(MediaType.Image, "http://lorempixel.com/200/200", moment);
                    mediaRepository.save(media);
                }
            }
            
            Comment comment = new Comment(moment, profile1, String.format("神奇评论%d", i));
            commentRepository.save(comment);
        	
        	Article article = new Article();
        	article.setTitle(String.format("铲屎官必读文章(%d)", i + 1));
        	article.setSummary("简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介");
        	article.setContent("<p style=\"color:#63c;\">第一段</p><p>第二段</p>");
        	article.setCover("http://lorempixel.com/400/200");
        	article.setProfile(i % 2 == 0 ? profile1 : profile2);
        	articleRepository.save(article);
        	
        	Question question = new Question();
        	question.setTitle("四川的猫吃辣吗？");
        	question.setContent("如题");
        	question.setProfile(i % 2 == 0 ? profile1 : profile2);
        	questionRepository.save(question);
        	
        	Answer answer = new Answer();
        	answer.setContent("<p style=\"color:#63c;\">吃的！吃的！</p>");
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
            	answer.setContent("<script>alert('xss!')</script>");
        		momentRepository.save(moment);
        		commentRepository.save(comment);
        		articleRepository.save(article);
        		questionRepository.save(question);
        		answerRepository.save(answer);
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
