package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;

public interface UserService {
    void createUser(User user);
    
    boolean checkUserPassword(UserCredentials cred);

    Iterable<User> findAll();
}
