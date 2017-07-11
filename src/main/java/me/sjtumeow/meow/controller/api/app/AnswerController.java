package me.sjtumeow.meow.controller.api.app;

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
import me.sjtumeow.meow.model.form.AddAnswerForm;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api")
public class AnswerController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping("/answers")
	Iterable<AnswerSummaryResult> getAnswers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllAnswers(false) : itemService.findAllAnswersPageable(page, size, false);
	}
	
	@PostMapping("/questions/{id}/answers")
	@Authorization
	ResponseEntity<?> addAnswer(@RequestBody AddAnswerForm aaf, @CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
		if (question == null)
			return ResponseEntity.notFound().build();
		
		String content = aaf.getContent();
		if (content == null || content.trim().isEmpty())
			return ResponseEntity.badRequest().body(new FailureMessageResult("回答内容不能为空"));

		return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(itemService.addAnswer(content, question, user)));
	}
	
	@DeleteMapping("/answers/{id}")
	@Authorization
	ResponseEntity<?> deleteAnswer(@CurrentUser User user, @PathVariable("id") Long id) {
		User creator = itemService.getAnswerCreator(id);
		if (creator == null)
			return ResponseEntity.notFound().build();
		if (creator.getId() != user.getId())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
        itemService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
	

