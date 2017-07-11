package me.sjtumeow.meow.controller.api.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.form.UserCredentialsForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.UserService;

@RestController
@RequestMapping("/api/web/auth")
public class WebAuthController {
	@Autowired
    private UserService userService;
	
	@PostMapping(consumes = "application/json")
	ResponseEntity<?> login(HttpSession session, @RequestBody UserCredentialsForm cred) {
		if (userService.checkPassword(cred)) {
			if (userService.isBanned(cred.getPhone())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FailureMessageResult("您的账号已被封禁!"));
			}
			session.setAttribute("user", userService.findByPhone(cred.getPhone(), false).getId());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FailureMessageResult("手机号或密码错误"));
		}
	}
	
	@DeleteMapping
	ResponseEntity<?> logout(HttpSession session) {
		session.removeAttribute("user");
		return ResponseEntity.noContent().build();
	}
}
