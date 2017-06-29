package me.sjtumeow.meow.mock;

import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SeederRunner implements ApplicationRunner {
    @Autowired
    MomentRepository momentRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired UserRepository userRepository;
    @Autowired ProfileRepository profileRepository;

    @Override
    public void run(ApplicationArguments args) {
        // Put some initializing code here
        for(int i = 0;i<10;i++) {
            momentRepository.save(new Moment());
            articleRepository.save(new Article());
        }
        User user = new User();

        userRepository.save(user);
        Profile profile = new Profile();

        profile.setUser(user);
        profileRepository.save(profile);


    }

}