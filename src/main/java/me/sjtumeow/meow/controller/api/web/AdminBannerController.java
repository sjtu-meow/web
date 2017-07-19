package me.sjtumeow.meow.controller.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.form.UpdateBannerForm;
import me.sjtumeow.meow.model.result.BannerResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.BannerService;

@RestController
@RequestMapping("/api/admin/banners")
public class AdminBannerController {
	
	@Autowired
    private BannerService bannerService;
	
	@GetMapping
	List<BannerResult> getBanners() {
		return bannerService.findAll();
	}
	
	@PutMapping(consumes = "application/json")
	ResponseEntity<?> updateBanners(@RequestBody List<UpdateBannerForm> ubfl) {
		String result = bannerService.update(ubfl);
		return result.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.badRequest().body(new FailureMessageResult(result));
	}

}
