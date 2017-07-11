package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.form.UpdateMomentForm;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api/admin/moments")
public class AdminMomentController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<Moment> getMoments(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllMoments(true) : itemService.findAllMomentsPageable(page, size, true);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getMoment(@PathVariable("id") Long id) {
		Moment moment = itemService.findMomentById(id, true);
        return moment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(moment);
	}
	
	@PatchMapping("/{id}")
	ResponseEntity<?> updateMoment(@RequestBody UpdateMomentForm umf, @PathVariable("id") Long id) {
		return itemService.updateMoment(id, umf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteMoment(@PathVariable("id") Long id) {
        return itemService.deleteMoment(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
