package me.sjtumeow.meow.controller.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;

@RestController
@RequestMapping("/api/admin/articles")
public class AdminArticleController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<ArticleSummaryResult> getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllArticles(true) : itemService.findAllArticlesPageable(page, size, true);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
		Article article = itemService.findArticleById(id, true);
        return article == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(article);
	}

}
