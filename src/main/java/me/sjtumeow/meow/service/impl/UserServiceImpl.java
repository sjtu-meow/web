package me.sjtumeow.meow.service.impl;

import java.util.List;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override public List<User> findAll() {
        return userRepository.findAll();
    }
}
