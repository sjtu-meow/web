package me.sjtumeow.meow.service;

import java.util.List;
import me.sjtumeow.meow.model.User;

public interface UserService {
    void createUser(User user);

    Iterable<User> findAll();
}
