package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.form.UpdateAnswerForm;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/admin/answers")
public class AdminAnswerController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping("/{id}")
	ResponseEntity<?> getAnswer(@PathVariable("id") Long id) {
		Answer answer = itemService.findAnswerById(id, true);
        return answer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(answer);
	}
	
	@PatchMapping("/{id}")
	ResponseEntity<?> updateAnswer(@RequestBody UpdateAnswerForm uaf, @PathVariable("id") Long id) {
		return itemService.updateAnswer(id, uaf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteAnswer(@PathVariable("id") Long id) {
		return itemService.deleteAnswer(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
