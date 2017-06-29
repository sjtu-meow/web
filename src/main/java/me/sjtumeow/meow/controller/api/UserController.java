package me.sjtumeow.meow.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @JsonView(User.Views.Public.class)
    @GetMapping()
    Iterable<User> getUsers() {
        return userRepository.findAllActive();
    }

    @JsonView(User.Views.Public.class)
    @GetMapping("/{id}")
    User getUser(@PathVariable("id") Long id) {
        return userRepository.findOneActive(id);
    }

    @PostMapping()
    ResponseEntity<?> createUser(User user) {

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

}