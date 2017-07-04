package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.authorization.manager.TokenManager;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
	private TokenManager tokenManager;
    
    public Iterable<User> findAll(boolean isAdmin) {
        return isAdmin ? userRepository.findAll() : userRepository.findAllActive();
    }
    
    public User findById(Long id, boolean isAdmin) {
    	return isAdmin ? userRepository.findOne(id) : userRepository.findOneActive(id);
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
	
	public Profile getProfile(Long id, boolean isAdmin) {
		return isAdmin ? profileRepository.findOne(id) : profileRepository.findOneActive(id);
	}
	
	@Transactional
	public void UpdateProfile(User user, ProfileForm pf) {
		Profile profile = user.getProfile();
		profile.setNickname(pf.getNickname());
		profile.setBio(pf.getBio());
		profile.setAvatar(pf.getAvatar());
	}
	
	@Transactional
    public Long create(String phone, String password, boolean isAdmin) {
    	User user = new User(phone, password);
    	user.setAdmin(isAdmin);
        userRepository.save(user);
        Profile profile = new Profile();
        profile.setUser(user);
        profileRepository.save(profile);
    	return user.getId();
    }
    
	@Transactional
    public boolean changePassword(Long id, String password) {
    	User user = userRepository.findOneActive(id);
    	if (user == null)
    		return false;
    	
    	user.setPassword(password);
    	userRepository.save(user);
    	return true;
    }
    
	@Transactional
    public boolean delete(Long id) {
    	if (!userRepository.existsActive(id))
    		return false;
    	userRepository.softDelete(id);
    	tokenManager.deleteToken(id);
    	return true;
    }

}
