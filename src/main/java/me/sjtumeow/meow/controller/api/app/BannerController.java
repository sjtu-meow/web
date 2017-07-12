package me.sjtumeow.meow.controller.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.result.BannerResult;
import me.sjtumeow.meow.service.BannerService;

@RestController
@RequestMapping("/api/banners")
public class BannerController {
	@Autowired
    private BannerService bannerService;
	
	@GetMapping
	List<BannerResult> getBanners() {
		return bannerService.findAll();
	}

}
