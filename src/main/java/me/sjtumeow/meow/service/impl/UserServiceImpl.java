package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    public Iterable<User> findAll() {
        return userRepository.findAllActive();
    }
    
    public User findById(Long id) {
    	return userRepository.findOneActive(id);
    }
	
	public User findByPhone(String phone) {
		User user = userRepository.findByPhone(phone);
		return (user != null && userRepository.existsActive(user.getId())) ? user : null;
	}
	
	public boolean checkPassword(UserCredentialsForm cred) {
    	User user = findByPhone(cred.getPhone());
    	// To be modified to BCrypt checking
    	return !(user == null || !user.getPassword().equals(cred.getPassword()));
    }
	
	public Profile getProfile(Long id) {
		return profileRepository.findOneActive(id);
	}

    public Long create(String phone, String password) {
    	User user = new User(phone, password);
        userRepository.save(user);
    	return user.getId();
    }
    
    public boolean changePassword(Long id, String password) {
    	User user = userRepository.findOneActive(id);
    	if (user == null)
    		return false;
    	
    	user.setPassword(password);
    	userRepository.save(user);
    	return true;
    }
    
    public boolean delete(Long id) {
    	if (!userRepository.existsActive(id))
    		return false;
    	userRepository.softDelete(id);
    	return true;
    }

}
