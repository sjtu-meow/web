package me.sjtumeow.meow.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/moments")
public class MomentController {
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<Moment> getMoments(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return itemService.findAllMoments(page, size);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getMoment(@PathVariable Long id) {
		Moment moment = itemService.findMomentById(id);
        return moment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(moment);
	}
	
	@PostMapping(consumes = "application/json")
	@Authorization
	ResponseEntity<?> addMoment(@RequestBody AddMomentForm amf, @CurrentUser User user) {
		return itemService.addMoment(amf, user) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.badRequest().body(new FailureMessageResult("点滴格式不正确"));
	}
	
	@DeleteMapping("/{id}")
	@Authorization
	ResponseEntity<?> deleteMoment(@CurrentUser User user, @PathVariable("id") Long id) {
		User creator = itemService.getMomentCreator(id);
		if (creator == null)
			return ResponseEntity.notFound().build();
		if (creator.getId() != user.getId())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
        itemService.deleteMoment(id);
        return ResponseEntity.noContent().build();
    }
}
