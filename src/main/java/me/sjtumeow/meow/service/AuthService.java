package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.model.result.TokenResult;

public interface AuthService {
	
	TokenResult generateUserToken(UserCredentialsForm cred);
	
	void deleteUserToken(User user);
	
	boolean verifySmsCode(String phone, String code);
}
