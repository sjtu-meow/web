package me.sjtumeow.meow.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    private static final String ERROR_PAGE = "/error";

    @RequestMapping(value = ERROR_PAGE)
    public String error(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PAGE;
    }
}
