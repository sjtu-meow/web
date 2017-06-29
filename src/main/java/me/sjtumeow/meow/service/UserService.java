package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;

public interface UserService {
	
	Iterable<User> findAll();
	
	User findById(Long id);
	
	User findByPhone(String phone);
	
    Long create(String phone, String password);
    
    boolean update(Long id, UserCredentials cred);
    
    public boolean delete(Long id);
    
    boolean checkPassword(UserCredentials cred);

}
