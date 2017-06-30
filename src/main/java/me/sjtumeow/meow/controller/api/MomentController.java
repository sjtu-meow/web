package me.sjtumeow.meow.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api")
public class MomentController {
	@Autowired
    private ItemService itemService;
	
	@GetMapping("/moments")
	Iterable<Moment> getMoments(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return itemService.getMoments(page, size);
	}
}
