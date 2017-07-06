package me.sjtumeow.meow.controller.api;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ChangePasswordForm;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.RegisterForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.AuthService;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;
    

    @PostMapping(path = "/users", consumes = "application/json")
    ResponseEntity<?> createUser(@RequestBody RegisterForm rp) {
    	
    	String phone = rp.getPhone();
    	String password = rp.getPassword();
    	String code = rp.getCode();
    	
    	if (!FormatValidator.checkPhone(phone))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("手机号格式不正确"));
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (!FormatValidator.checkSmsCode(code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码必须是 6 位数字"));
    	if (!authService.verifySmsCode(phone, code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码验证失败"));
    	if (userService.findByPhone(phone, true) != null)
			return ResponseEntity.badRequest().body(new FailureMessageResult("该手机号已被注册"));
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(userService.create(phone, password)));
    }
    
    @Authorization
    @PutMapping(path = "/user", consumes = "application/json")
    ResponseEntity<?> changePassword(@CurrentUser User user, @RequestBody ChangePasswordForm cpf) {
    	
    	String phone = user.getPhone();
    	String password = cpf.getPassword();
    	String code = cpf.getCode();
    	
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (!FormatValidator.checkSmsCode(code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码必须是 6 位数字"));
    	if (!authService.verifySmsCode(phone, code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码验证失败"));
    	
    	return userService.changePassword(user.getId(), password) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    	
    }
    
    @GetMapping("/user/profile")
    @Authorization
    Profile getProfile(@CurrentUser User user) {
    	return user.getProfile();
    }
    
    @PutMapping(path = "/user/profile", consumes = "application/json")
    @Authorization
    ResponseEntity<?> updateProfile(@CurrentUser User user, @RequestBody ProfileForm pf) {
    	userService.UpdateProfile(user, pf);
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/users/{id}/profile")
    ResponseEntity<?> getProfile(@PathVariable("id") Long id) {
    	Profile profile = userService.getProfile(id, false);
    	return profile == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(profile);
    }
}