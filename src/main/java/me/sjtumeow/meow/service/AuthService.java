package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.TokenReturned;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentialsForm;

public interface AuthService {
	
	TokenReturned generateUserToken(UserCredentialsForm cred);
	
	void deleteUserToken(User user);
	
	boolean verifySmsCode(String phone, String code);
}
