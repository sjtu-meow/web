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

import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.form.UpdateQuestionForm;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<?> getQuestions(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) String keyword) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllQuestions(StringUtil.replaceNull(keyword), true) : itemService.findAllQuestionsPageable(page, size, StringUtil.replaceNull(keyword), true);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getQuestion(@PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, true);
        return question == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(question);
	}
	
	@PatchMapping("/{id}")
	ResponseEntity<?> updateQuestion(@RequestBody UpdateQuestionForm uqf, @PathVariable("id") Long id) {
		return itemService.updateQuestion(id, uqf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteQuestion(@PathVariable("id") Long id) {
		return itemService.deleteQuestion(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
