package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    public Iterable<User> findAll() {
    	return userRepository.findAll();
        //return userRepository.findAllActive();
    }
    
    public User findById(Long id) {
    	return userRepository.findById(id);
    }
	
	public User findByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

    public Long create(String phone, String password) {
    	User user = new User(phone, password);
        userRepository.save(user);
    	return user.getId();
    }
    
    public boolean update(Long id, UserCredentials cred) {
    	User user = userRepository.findById(id);
    	if (user == null)
    		return false;
    	
    	user.setPhone(cred.getPhone());
    	user.setPassword(cred.getPassword());
    	userRepository.save(user);
    	return true;
    }
    
    public boolean delete(Long id) {
    	if (!userRepository.exists(id))
    		return false;
    	userRepository.delete(id);
    	return true;
    }

    public boolean checkPassword(UserCredentials cred) {
    	User user = userRepository.findByPhone(cred.getPhone());
    	// To be modified to BCrypt checking
    	return !(user == null || !user.getPassword().equals(cred.getPassword()));
    }
    
}
