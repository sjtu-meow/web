package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.UserCredentialsForm;

public interface UserService {
	
	Iterable<User> findAll(boolean isAdmin);
	
	User findById(Long id, boolean isAdmin);
	
	User findByPhone(String phone);
	
	boolean checkPassword(UserCredentialsForm cred);
	
	Profile getProfile(Long id, boolean isAdmin);
	
	void UpdateProfile(User user, ProfileForm pf);
	
    Long create(String phone, String password);
    
    boolean changePassword(Long id, String password);
    
    public boolean delete(Long id);
    
}
