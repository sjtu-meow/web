package me.sjtumeow.meow.mock;

import me.sjtumeow.meow.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SeederRunner implements ApplicationRunner {

    @Autowired UserRepository userRepository;
    public void run(ApplicationArguments args) {
        // Put some initializing code here
    }

}