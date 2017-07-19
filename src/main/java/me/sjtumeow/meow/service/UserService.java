package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.model.form.AdminUpdateUserForm;

public interface UserService {
	
	Iterable<User> findAll(String keyword, boolean isAdmin);
	
	Iterable<User> findAllPageable(Integer page, Integer size, String keyword, boolean isAdmin);
	
	User findById(Long id, boolean isAdmin);
	
	User findByPhone(String phone, boolean withTrash);
	
	boolean isBanned(String phone);
	
	boolean checkPassword(UserCredentialsForm cred);
	
	Profile getProfile(Long id, boolean isAdmin);
	
	void UpdateProfile(User user, ProfileForm pf);
	
    Long create(String phone, String password);
    
    Long create(String phone, String password, boolean isAdmin, String nickname, String bio, String avatar);
    
    boolean changePassword(Long id, String password);
    
    boolean update(Long id, AdminUpdateUserForm auuf);
    
    boolean delete(Long id);
    
}
