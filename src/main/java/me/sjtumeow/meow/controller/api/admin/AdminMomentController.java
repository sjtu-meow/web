package me.sjtumeow.meow.controller.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/admin/moments")
public class AdminMomentController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<Moment> getMoments(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return itemService.findAllMoments(page, size, true);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getMoment(@PathVariable("id") Long id) {
		Moment moment = itemService.findMomentById(id, true);
        return moment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(moment);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteMoment(@PathVariable("id") Long id) {
        return itemService.deleteMoment(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
