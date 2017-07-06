package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.authorization.manager.TokenManager;
import me.sjtumeow.meow.dao.ProfileRepository;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AdminUpdateUserForm;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.service.UserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    
    public Iterable<User> findAllPageable(Integer page, Integer size, boolean isAdmin) {
    	return isAdmin ? userRepository.findAll(new PageRequest(page, size)) : userRepository.findAllActive(new PageRequest(page, size));
    }
    
    public User findById(Long id, boolean isAdmin) {
    	return isAdmin ? userRepository.findOne(id) : userRepository.findOneActive(id);
    }
	
	public User findByPhone(String phone, boolean withTrash) {
		if (withTrash) {
			return userRepository.findByPhone(phone);
		} else {
			User user = userRepository.findByPhone(phone);
			return (user != null && userRepository.existsActive(user.getId())) ? user : null;
		}
	}
	
	public boolean isBanned(String phone) {
		User user = findByPhone(phone, true);
		return user != null && user.isDeleted();
	}
	
	public boolean checkPassword(UserCredentialsForm cred) {
    	User user = findByPhone(cred.getPhone(), true);
    	return user != null && BCrypt.checkpw(cred.getPassword(), user.getPassword());
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
    public Long create(String phone, String password) {
    	User user = new User(phone, BCrypt.hashpw(password, BCrypt.gensalt()));
        userRepository.save(user);
        Profile profile = new Profile();
        profile.setUser(user);
        profileRepository.save(profile);
    	return user.getId();
    }
	
	@Transactional
	public Long create(String phone, String password, boolean isAdmin, String nickname, String bio, String avatar) {
		User user = new User(phone, BCrypt.hashpw(password, BCrypt.gensalt()));
    	user.setAdmin(isAdmin);
        userRepository.save(user);
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setNickname(nickname);
        profile.setBio(bio);
        profile.setAvatar(avatar);
        profileRepository.save(profile);
    	return user.getId();
	}
    
	@Transactional
    public boolean changePassword(Long id, String password) {
    	User user = userRepository.findOneActive(id);
    	if (user == null)
    		return false;
    	
    	user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    	userRepository.save(user);
    	return true;
    }
	
	@Transactional
	public boolean update(Long id, AdminUpdateUserForm auuf) {
		User user = userRepository.findOne(id);
		if (user == null)
    		return false;
		
		if (auuf.getPassword() != null)
			user.setPassword(BCrypt.hashpw(auuf.getPassword(), BCrypt.gensalt()));
		if (auuf.getIsAdmin() != null)
			user.setAdmin(auuf.getIsAdmin());
		if (auuf.getIsDeleted() != null && !auuf.getIsDeleted())
			user.recover();
		
		userRepository.save(user);
		
		Profile profile = user.getProfile();
		if (auuf.getNickname() != null)
			profile.setNickname(auuf.getNickname());
		if (auuf.getBio() != null)
			profile.setBio(auuf.getBio());
		if (auuf.getAvatar() != null)
			profile.setAvatar(auuf.getAvatar());
		
		profileRepository.save(profile);
		
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
