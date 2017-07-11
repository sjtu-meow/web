package me.sjtumeow.meow.controller.api.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.web.WebAuthUtility;
import me.sjtumeow.meow.service.UploadService;

@RestController
@RequestMapping("/api/web/upload/token")
public class WebUploadController {
	
	@Autowired
    private UploadService uploadService;
	
	@Autowired
	private WebAuthUtility webAuthUtility;
	
	@GetMapping
	ResponseEntity<?> getUploadToken(HttpSession session) {
		if (!webAuthUtility.checkAuth(session))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(uploadService.getUploadToken());
	}
}
