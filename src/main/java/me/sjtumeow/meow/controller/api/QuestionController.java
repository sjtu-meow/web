package me.sjtumeow.meow.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<QuestionSummaryResult> getQuestions(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllQuestions(false) : itemService.findAllQuestionsPageable(page, size, false);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getQuestion(@PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        return question == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(question);
	}
}
