package me.sjtumeow.meow.controller.api.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.service.UploadService;

@RestController
@RequestMapping("/api/web/upload/token")
public class WebUploadController {

    @Autowired
    private UploadService uploadService;

    @GetMapping
    ResponseEntity<?> getUploadToken(HttpSession session) {
        return ResponseEntity.ok(uploadService.getUploadToken());
    }
}
