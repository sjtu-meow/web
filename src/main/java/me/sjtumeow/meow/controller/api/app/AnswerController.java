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
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddAnswerForm;
import me.sjtumeow.meow.model.result.AnswerDetailResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.FavoriteStatusResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api")
public class AnswerController {
	
	@Autowired
    private ItemService itemService;
	
	@Autowired
	private InteractionService interactionService;
	
	@GetMapping("/answers")
	Iterable<?> getAnswers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllAnswers(false) : itemService.findAllAnswersPageable(page, size, false);
	}
	
	@GetMapping("/answers/{id}")
	ResponseEntity<?> getAnswer(@PathVariable("id") Long id) {
		AnswerDetailResult answer = itemService.showAnswerById(id);
        return answer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(answer);
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
	
	@GetMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> checkFavoriteAnswer(@CurrentUser User user, @PathVariable("id") Long id) {
		Answer answer = itemService.findAnswerById(id, false);
        return answer == null ? ResponseEntity.notFound().build() :
        	ResponseEntity.ok(new FavoriteStatusResult(interactionService.checkFavorite(user, answer)));
	}
	
	@PostMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> doFavoriteAnswer(@CurrentUser User user, @PathVariable("id") Long id) {
		Answer answer = itemService.findAnswerById(id, false);
        if (answer == null)
        	return ResponseEntity.notFound().build();
        interactionService.doFavorite(user, answer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> cancelFavoriteAnswer(@CurrentUser User user, @PathVariable("id") Long id) {
		Answer answer = itemService.findAnswerById(id, false);
        if (answer == null)
        	return ResponseEntity.notFound().build();
        interactionService.cancelFavorite(user, answer);
        return ResponseEntity.noContent().build();
	}
}
	

