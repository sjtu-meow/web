package me.sjtumeow.meow.controller.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AdminChangePasswordForm;
import me.sjtumeow.meow.model.form.AdminRegisterForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
	
	@Autowired
    private UserService userService;
	
	@JsonView(User.Views.Public.class)
    @GetMapping
    Iterable<User> getUsers() {
        return userService.findAll(true);
    }
	
	@JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id, true);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
	
	@GetMapping("/{id}/profile")
    ResponseEntity<?> getProfile(@PathVariable("id") Long id) {
    	Profile profile = userService.getProfile(id, true);
    	return profile == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(profile);
    }
	
	@PostMapping(consumes = "application/json")
    ResponseEntity<?> createUser(@RequestBody AdminRegisterForm arp) {
    	
    	String phone = arp.getPhone();
    	String password = arp.getPassword();
    	
    	if (!FormatValidator.checkPhone(phone))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("手机号格式不正确"));
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (userService.findByPhone(phone) != null)
			return ResponseEntity.badRequest().body(new FailureMessageResult("该手机号已被注册"));
    	
        userService.create(phone, password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
    @PutMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> changePassword(@PathVariable("id") Long id, @RequestBody AdminChangePasswordForm acpf) {
    	
    	String password = acpf.getPassword();
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	
    	return userService.changePassword(id, password) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build();
    	
    }
	
	@DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
