package me.sjtumeow.meow.controller.api.web;

import javax.servlet.http.HttpSession;

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

import me.sjtumeow.meow.authorization.web.WebAuthUtility;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AdminRegisterForm;
import me.sjtumeow.meow.model.form.AdminUpdateUserForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebAuthUtility webAuthUtility;

    @GetMapping
    Iterable<User> getUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? userService.findAll(StringUtil.replaceNull(keyword), true)
                : userService.findAllPageable(page, size, StringUtil.replaceNull(keyword), true);
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

        return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(
                userService.create(phone, password, arp.isAdmin(), arp.getNickname(), arp.getBio(), arp.getAvatar())));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody AdminUpdateUserForm auuf,
            HttpSession session) {

        String password = auuf.getPassword();
        if (password != null && !FormatValidator.checkPassword(password))
            return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
        if (webAuthUtility.getCurrentUser(session).getId().equals(id) && auuf.getIsAdmin() != null
                && !auuf.getIsAdmin())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FailureMessageResult("禁止修改本人权限"));

        return userService.update(id, auuf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id, HttpSession session) {
        if (webAuthUtility.getCurrentUser(session).getId().equals(id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FailureMessageResult("禁止删除当前用户"));
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
