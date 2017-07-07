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
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddQuestionForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
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
	
	@PostMapping(consumes = "application/json")
	@Authorization
	ResponseEntity<?> addQuestion(@RequestBody AddQuestionForm aqf, @CurrentUser User user) {
		String title = aqf.getTitle();
		if (title == null || title.trim().isEmpty())
			return ResponseEntity.badRequest().body(new FailureMessageResult("问题标题不能为空"));

		return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(itemService.addQuestion(aqf, user)));
	}
	
	@DeleteMapping("/{id}")
	@Authorization
	ResponseEntity<?> deleteQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		User creator = itemService.getQuestionCreator(id);
		if (creator == null)
			return ResponseEntity.notFound().build();
		if (creator.getId() != user.getId())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
        itemService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
