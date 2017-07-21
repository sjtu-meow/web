package me.sjtumeow.meow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getIndex() {
        return "login";
    }

    @GetMapping("/editor")
    public String getEditor() {
        return "editor";
    }

    @GetMapping("/admin")
    public String getAdminIndex() {
        return "admin";
    }
}
