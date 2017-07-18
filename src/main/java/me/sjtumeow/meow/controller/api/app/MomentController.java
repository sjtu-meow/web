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
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.MomentDetailResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/moments")
public class MomentController {
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<?> getMoments(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) String keyword) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllMoments(StringUtil.replaceNull(keyword), false) : itemService.findAllMomentsPageable(page, size, StringUtil.replaceNull(keyword), false);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getMoment(@PathVariable("id") Long id) {
		MomentDetailResult moment = itemService.showMomentById(id);
        return moment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(moment);
	}
	
	@PostMapping(consumes = "application/json")
	@Authorization
	ResponseEntity<?> addMoment(@RequestBody AddMomentForm amf, @CurrentUser User user) {
		CreateResult cr = itemService.addMoment(amf, user);
		return cr.isResult() ? ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(cr.getId())) : ResponseEntity.badRequest().body(new FailureMessageResult("点滴格式不正确"));
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
