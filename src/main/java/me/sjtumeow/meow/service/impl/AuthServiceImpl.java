package me.sjtumeow.meow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.authorization.manager.TokenManager;
import me.sjtumeow.meow.authorization.model.TokenModel;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.ReturnedToken;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;
import me.sjtumeow.meow.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
    private UserRepository userRepository;

	public ReturnedToken generateUserToken(UserCredentials cred) {
		Long userId = userRepository.findByPhone(cred.getPhone()).getId();
		tokenManager.deleteToken(userId);
		TokenModel tokenModel = tokenManager.createToken(userId);
		return new ReturnedToken(tokenModel.getUserId() + "_" + tokenModel.getToken());
	}
	
	public void deleteUserToken(User user) {
		tokenManager.deleteToken(user.getId());
	}

}
