package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.ReturnedToken;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;

public interface AuthService {
	
	ReturnedToken generateUserToken(UserCredentials cred);
	
	void deleteUserToken(User user);
}
