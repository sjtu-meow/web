package me.sjtumeow.meow.controller.api;

import com.fasterxml.jackson.annotation.JsonView;

import me.sjtumeow.meow.model.RegisterParam;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.UserCredentials;
import me.sjtumeow.meow.service.AuthService;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

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

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;
    
    @JsonView(User.Views.Public.class)
    @GetMapping
    Iterable<User> getUsers() {
        return userService.findAll();
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping(consumes = "application/json")
    ResponseEntity<?> createUser(@RequestBody RegisterParam rp) {
    	
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
    
    @PutMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserCredentials cred) {
    	
    	String phone = cred.getPhone();
    	String password = cred.getPassword();
    	
    	if (!FormatValidator.checkPhone(phone) || !FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().build();
    	
    	User user = userService.findById(id);
    	if (user != null && !user.getPhone().equals(phone) && userService.findByPhone(phone) != null)
    		return ResponseEntity.badRequest().build();
    	
    	return userService.update(id, cred) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build();
    	
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}