package me.sjtumeow.meow.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.result.AnswerSummaryResult;
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
}
	

