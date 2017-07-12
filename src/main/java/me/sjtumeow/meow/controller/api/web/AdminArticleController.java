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

import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.form.UpdateArticleForm;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/articles")
public class AdminArticleController {
	
	@Autowired
    private ItemService itemService;
	
	@GetMapping
	Iterable<?> getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) String keyword) {
		return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAllArticles(StringUtil.replaceNull(keyword), true) : itemService.findAllArticlesPageable(page, size, StringUtil.replaceNull(keyword), true);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
		Article article = itemService.findArticleById(id, true);
        return article == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(article);
	}
	
	@PatchMapping("/{id}")
	ResponseEntity<?> updateArticle(@RequestBody UpdateArticleForm uaf, @PathVariable("id") Long id) {
		return itemService.updateArticle(id, uaf) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) {
		return itemService.deleteArticle(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
