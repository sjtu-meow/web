package me.sjtumeow.meow.controller.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.model.result.QiniuTokenResult;
import me.sjtumeow.meow.service.UploadService;

@RestController
@RequestMapping("/api/upload/token")
public class UploadController {
	
	@Autowired
    private UploadService uploadService;
	
	@GetMapping
	@Authorization
	QiniuTokenResult getUploadToken() {
		return uploadService.getUploadToken();
	}
	
}
