package me.sjtumeow.meow.mock;

import com.github.javafaker.Faker;
import javax.persistence.OneToOne;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Media;
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

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) {
        // Put some initializing code here
        /*for(int i = 0; i < 10; i++) {
            momentRepository.save(new Moment());
            articleRepository.save(new Article());
        }*/
        
    	User user = new User("12312312312", "meow");
    	user.setAdmin(true);
    	userRepository.save(user);
        userRepository.save(new User("32132132132", "test"));
        
        Profile profile = new Profile();
        profile.setNickname("喵喵喵的伙伴");
        profile.setBio("Web 开发专家");
        profile.setUser(user);
        profileRepository.save(profile);
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
