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
import me.sjtumeow.meow.model.result.ArticleDetailResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.FavoriteStatusResult;
import me.sjtumeow.meow.model.result.LikeStatusResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private InteractionService interactionService;

    @GetMapping
    Iterable<?> getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? itemService.findAllArticles(StringUtil.replaceNull(keyword), false)
                : itemService.findAllArticlesPageable(page, size, StringUtil.replaceNull(keyword), false);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
        ArticleDetailResult article = itemService.showArticleById(id);
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

    @GetMapping("/{id}/like")
    @Authorization
    ResponseEntity<?> checkLikeArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        return article == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new LikeStatusResult(interactionService.checkLike(user, article)));
    }

    @PostMapping("/{id}/like")
    @Authorization
    ResponseEntity<?> doLikeArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        if (article == null)
            return ResponseEntity.notFound().build();
        interactionService.doLike(user, article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/like")
    @Authorization
    ResponseEntity<?> cancelLikeArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        if (article == null)
            return ResponseEntity.notFound().build();
        interactionService.cancelLike(user, article);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/favorite")
    @Authorization
    ResponseEntity<?> checkFavoriteArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        return article == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new FavoriteStatusResult(interactionService.checkFavorite(user, article)));
    }

    @PostMapping("/{id}/favorite")
    @Authorization
    ResponseEntity<?> doFavoriteArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        if (article == null)
            return ResponseEntity.notFound().build();
        interactionService.doFavorite(user, article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/favorite")
    @Authorization
    ResponseEntity<?> cancelFavoriteArticle(@CurrentUser User user, @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        if (article == null)
            return ResponseEntity.notFound().build();
        interactionService.cancelFavorite(user, article);
        return ResponseEntity.noContent().build();
    }
}
