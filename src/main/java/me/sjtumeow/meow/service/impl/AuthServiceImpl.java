package me.sjtumeow.meow.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.authorization.manager.TokenManager;
import me.sjtumeow.meow.authorization.model.TokenModel;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.TokenReturned;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentialsForm;
import me.sjtumeow.meow.service.AuthService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AuthServiceImpl implements AuthService {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	@Value("${leanCloud.appId}")
	private String appId;
	
	@Value("${leanCloud.appKey}")
	private String appKey;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
    private UserRepository userRepository;

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public TokenReturned generateUserToken(UserCredentialsForm cred) {
		Long userId = userRepository.findByPhone(cred.getPhone()).getId();
		tokenManager.deleteToken(userId);
		TokenModel tokenModel = tokenManager.createToken(userId);
		return new TokenReturned(tokenModel.getUserId() + "_" + tokenModel.getToken());
	}
	
	public void deleteUserToken(User user) {
		tokenManager.deleteToken(user.getId());
	}
	
	public boolean verifySmsCode(String phone, String code) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url(String.format("https://api.leancloud.cn/1.1/verifySmsCode/%s?mobilePhoneNumber=%s", code, phone))
		  .post(RequestBody.create(JSON, ""))
		  .addHeader("X-LC-Id", appId)
		  .addHeader("X-LC-Key", appKey)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Cache-Control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			return response.code() == 200;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
