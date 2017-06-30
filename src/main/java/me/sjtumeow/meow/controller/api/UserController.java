package me.sjtumeow.meow.controller.api;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.ChangePasswordForm;
import me.sjtumeow.meow.model.RegisterForm;
import me.sjtumeow.meow.model.User;
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
    	
    	if (!FormatValidator.checkPhone(phone) || !FormatValidator.checkPassword(password)
    			|| !FormatValidator.checkSmsCode(code) || !authService.verifySmsCode(phone, code)
    			|| userService.findByPhone(phone) != null)
    		return ResponseEntity.badRequest().build();
    	
        userService.create(phone, password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @Authorization
    @PutMapping(path = "/user", consumes = "application/json")
    ResponseEntity<?> changePassword(@CurrentUser User user, @RequestBody ChangePasswordForm cpf) {
    	
    	String phone = user.getPhone();
    	String password = cpf.getPassword();
    	String code = cpf.getCode();
    	
    	if (!FormatValidator.checkPassword(password) || !FormatValidator.checkSmsCode(code)
    			|| !authService.verifySmsCode(phone, code))
    		return ResponseEntity.badRequest().build();
    	
    	return userService.changePassword(user.getId(), password) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build();
    	
    }

    /*@DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }*/

}