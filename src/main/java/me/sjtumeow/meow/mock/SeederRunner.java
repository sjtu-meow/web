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
        
        Profile profile = new Profile();
        profile.setNickname("喵喵喵的伙伴");
        profile.setBio("Web 开发专家");
        profile.setUser(user1);
        profileRepository.save(profile);
        
        for (int j = 0; j < 10; j++) {
            Moment moment = new Moment();
            moment.setProfile(profile);
            moment.setContent(faker.shakespeare().hamletQuote());
            momentRepository.save(moment);
            
            Media media = new Media();
            media.setType(MediaType.Image);
            media.setUrl(String.format("http://lorempixel.com/%d/%d", 200 + j, 200 + j));
            media.setThumbnail("http://lorempixel.com/50/50");
            media.setMoment(moment);
            mediaRepository.save(media);
            //momentRepository.save(moment);
            
            //moment.addMedia(media);
            
            Comment comment = new Comment(moment, profile, "神评论");
            commentRepository.save(comment);
            //momentRepository.save(moment);
            
            Banner banner = new Banner();
        	banner.setUrl("xxx");
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
