package me.sjtumeow.meow.mock;

import com.github.javafaker.Faker;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Media.MediaType;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
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

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) {
        // Put some initializing code here
        /*for(int i = 0; i < 10; i++) {
            momentRepository.save(new Moment());
            articleRepository.save(new Article());
        }*/
        
    	User user1 = new User("12312312312", "meow233");
    	User user2 = new User("12132132132", "test123");
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
        profile2.setBio("lalala lababa");
        profile2.setUser(user2);
        profileRepository.save(profile2);
        
        for (int j = 0; j < 10; j++) {
            Moment moment = new Moment();
            moment.setProfile(profile1);
            moment.setContent(faker.shakespeare().hamletQuote());
            momentRepository.save(moment);
            
            Media media = new Media(MediaType.Image, String.format("http://lorempixel.com/%d/%d", 200 + j, 200 + j), moment);
            mediaRepository.save(media);
            momentRepository.save(moment);
            
         
             
            
            
            //moment.addMedia(media);
            
            Comment comment = new Comment(moment, profile1, "神评论");
            commentRepository.save(comment);
            //momentRepository.save(moment);
            
            Banner banner = new Banner();
        	banner.setUrl("http://lorempixel.com/200/200");
        	banner.setItem(moment);
        	bannerRepository.save(banner);
        }
        
  /*
        for(int i = 0;i<10;i++) {
            User user = new User();
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setPassword(faker.book().title());
            userRepository.save(user);
            Profile profile = new Profile();
            profile.setUser(user);
            profile.setNickname(faker.name().firstName());
            profileRepository.save(profile);

            for(int j = 0; j < 10; j++) {
                Moment moment = new Moment();
                Media media = new Media();

                media.setUrl("http://lorempixel.com/400/200");
                media.setThumbnail("http://lorempixel.com/400/200");
                mediaRepository.save(media);


                moment.setProfile(profile);
                moment.setContent(faker.shakespeare().hamletQuote());
                moment.addMedia(media);

                momentRepository.save(new Moment());


            }
        }
        userRepository.findAllActive();
*/
    }

}
