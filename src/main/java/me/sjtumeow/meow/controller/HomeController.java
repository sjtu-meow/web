package me.sjtumeow.meow.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import me.sjtumeow.meow.authorization.web.WebAuthUtility;

@Controller
public class HomeController {

    @Autowired
    private WebAuthUtility webAuthUtility;

    @GetMapping("/")
    public String getIndex(HttpSession session) {
        if (webAuthUtility.checkAdmin(session))
            return "redirect:/admin";
        if (webAuthUtility.checkAuth(session))
            return "redirect:/editor";

        return "login";
    }

    @GetMapping("/editor")
    public String getEditor(HttpSession session) {
        if (!webAuthUtility.checkAuth(session))
            return "redirect:/";

        return "editor";
    }

    @GetMapping("/admin")
    public String getAdminIndex(HttpSession session, HttpServletResponse response) {
        if (!webAuthUtility.checkAuth(session))
            return "redirect:/";
        if (!webAuthUtility.checkAdmin(session)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        return "admin";
    }
}
