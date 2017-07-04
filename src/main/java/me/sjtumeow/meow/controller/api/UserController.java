package me.sjtumeow.meow.controller.api;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ChangePasswordForm;
import me.sjtumeow.meow.model.form.RegisterForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.AuthService;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    /*@JsonView(User.Views.Public.class)
    @GetMapping
    Iterable<User> getUsers() {
        return userService.findAll();
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }*/

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
    	if (userService.findByPhone(phone) != null)
			return ResponseEntity.badRequest().body(new FailureMessageResult("该手机号已被注册"));
    	
        userService.create(phone, password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    	
    	return userService.changePassword(user.getId(), password) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build();
    	
    }

    /*@DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }*/

}