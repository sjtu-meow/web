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
        return userRepository.findAllActive();
    }
    
    public User findUserbyId(Long id) {
    	return userRepository.findById(id);
    }
	
	public User findUserbyPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

    public void createUser(User user) {
        userRepository.save(user);
    }

    public boolean checkUserPassword(UserCredentials cred) {
    	User user = userRepository.findByPhone(cred.getPhone());
    	// To be modified to BCrypt checking
    	return !(user == null || !user.getPassword().equals(cred.getPassword()));
    }
    
}
