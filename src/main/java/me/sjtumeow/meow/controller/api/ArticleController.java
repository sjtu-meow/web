package me.sjtumeow.meow.controller.api;

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
@RequestMapping("/api/articles")
public class ArticleController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<ArticleSummaryResult> getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllArticles(false) : itemService.findAllArticlesPageable(page, size, false);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getMoment(@PathVariable("id") Long id) {
		Article article = itemService.findArticleById(id, false);
        return article == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(article);
	}
}
