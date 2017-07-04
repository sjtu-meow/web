package me.sjtumeow.meow.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.service.AuthService;
import me.sjtumeow.meow.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private AuthService authService;
	
	@PostMapping(consumes = "application/json")
	ResponseEntity<?> login(@RequestBody UserCredentialsForm cred) {
		return userService.checkPassword(cred) ? ResponseEntity.ok(authService.generateUserToken(cred)) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	@Authorization
	ResponseEntity<?> logout(@CurrentUser User user) {
		authService.deleteUserToken(user);
		return ResponseEntity.noContent().build();
	}
}
