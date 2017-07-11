package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AdminUpdateUserForm;
import me.sjtumeow.meow.model.form.AdminRegisterForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
	
	@Autowired
    private UserService userService;
	
    @GetMapping
    Iterable<User> getUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				userService.findAll(true) : userService.findAllPageable(page, size, true);
    }
	
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id, true);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
	
	@PostMapping(consumes = "application/json")
    ResponseEntity<?> createUser(@RequestBody AdminRegisterForm arp) {
    	
    	String phone = arp.getPhone();
    	String password = arp.getPassword();
    	
    	if (!FormatValidator.checkPhone(phone))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("手机号格式不正确"));
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (userService.findByPhone(phone, true) != null)
			return ResponseEntity.badRequest().body(new FailureMessageResult("该手机号已被注册"));

        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(new NewEntityIdResult(userService.create(phone, password, arp.isAdmin(), arp.getNickname(), arp.getBio(), arp.getAvatar())));
    }
	
    @PatchMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody AdminUpdateUserForm auuf) {
    	
    	String password = auuf.getPassword();
    	if (password != null && !FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	
    	return userService.update(id, auuf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    	
    }
	
	@DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
