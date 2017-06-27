package me.sjtumeow.meow.controller.api;

import java.util.List;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.RegisterForm;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users") List<User> getUsers(@RequestHeader(value = "X-Access-Token", required = false) String token) {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}") User getUser(@RequestHeader(value = "X-Access-Token", required = false) String token,
        @PathVariable("id") Long id) {
        return userRepository.findOne(id);
    }

    @PostMapping("/api/users") ResponseEntity<?> createUser(RegisterForm registerForm) {
        User user = new User();
        user.setPassword(registerForm.getPassword());
        user.setPhone(registerForm.getPhone());
        user.setUsername(registerForm.getUsername());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/users/{id}") ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

}