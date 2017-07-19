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
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddQuestionForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.FavoriteStatusResult;
import me.sjtumeow.meow.model.result.FollowStatusResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.model.result.QuestionDetailResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
    private ItemService itemService;
	
	@Autowired
	private InteractionService interactionService;
	
	@GetMapping("/{id}")
	ResponseEntity<?> getQuestion(@PathVariable("id") Long id) {
		QuestionDetailResult question = itemService.showQuestionById(id);
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
	
	@GetMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> checkFavoriteQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        return question == null ? ResponseEntity.notFound().build() :
        	ResponseEntity.ok(new FavoriteStatusResult(interactionService.checkFavorite(user, question)));
	}
	
	@PostMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> doFavoriteQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        if (question == null)
        	return ResponseEntity.notFound().build();
        interactionService.doFavorite(user, question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/{id}/favorite")
	@Authorization
	ResponseEntity<?> cancelFavoriteQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        if (question == null)
        	return ResponseEntity.notFound().build();
        interactionService.cancelFavorite(user, question);
        return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/follow")
	@Authorization
	ResponseEntity<?> checkFollowQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        return question == null ? ResponseEntity.notFound().build() :
        	ResponseEntity.ok(new FollowStatusResult(interactionService.checkFollowQuestion(user, question)));
	}
	
	@PostMapping("/{id}/follow")
	@Authorization
	ResponseEntity<?> doFollowQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        if (question == null)
        	return ResponseEntity.notFound().build();
        interactionService.doFollowQuestion(user, question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/{id}/follow")
	@Authorization
	ResponseEntity<?> cancelFollowQuestion(@CurrentUser User user, @PathVariable("id") Long id) {
		Question question = itemService.findQuestionById(id, false);
        if (question == null)
        	return ResponseEntity.notFound().build();
        interactionService.cancelFollowQuestion(user, question);
        return ResponseEntity.noContent().build();
	}
}
