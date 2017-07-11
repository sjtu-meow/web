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
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddArticleForm;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
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
	ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
		Article article = itemService.findArticleById(id, false);
        return article == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(article);
	}
	
	@PostMapping(consumes = "application/json")
	@Authorization
	ResponseEntity<?> addArticle(@RequestBody AddArticleForm aaf, @CurrentUser User user) {
		String title = aaf.getTitle();
		if (title == null || title.trim().isEmpty())
			return ResponseEntity.badRequest().body(new FailureMessageResult("文章标题不能为空"));
		
		String content = aaf.getContent();
		if (content == null || content.trim().isEmpty())
			return ResponseEntity.badRequest().body(new FailureMessageResult("文章内容不能为空"));
		
		String cover = aaf.getCover();
		if (cover == null || cover.trim().isEmpty())
			return ResponseEntity.badRequest().body(new FailureMessageResult("文章缺少封面图"));

		return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(itemService.addArticle(aaf, user)));
	}
	
	// TODO: PUT article?
	
	// TODO: Web article editor add article API, store/update(?) article
	
	@DeleteMapping("/{id}")
	@Authorization
	ResponseEntity<?> deleteArticle(@CurrentUser User user, @PathVariable("id") Long id) {
		User creator = itemService.getArticleCreator(id);
		if (creator == null)
			return ResponseEntity.notFound().build();
		if (creator.getId() != user.getId())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
        itemService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
