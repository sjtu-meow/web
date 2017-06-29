package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.ReturnedToken;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;

public interface AuthService {
	public ReturnedToken generateUserToken(UserCredentials cred);
	
	public void deleteUserToken(User user);
}
