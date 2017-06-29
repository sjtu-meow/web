package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;

public interface UserService {
	
	Iterable<User> findAll();
	
	User findUserbyId(Long id);
	
	User findUserbyPhone(String phone);
	
    void createUser(User user);
    
    // update, delete
    
    boolean checkUserPassword(UserCredentials cred);

}
