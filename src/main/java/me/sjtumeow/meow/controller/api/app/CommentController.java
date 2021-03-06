package me.sjtumeow.meow.controller.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddCommentForm;
import me.sjtumeow.meow.model.form.ReportForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private InteractionService interactionService;

    @PostMapping(path = "/moments/{id}/comments", consumes = "application/json")
    @Authorization
    ResponseEntity<?> addMomentComment(@RequestBody AddCommentForm acf, @CurrentUser User user,
            @PathVariable("id") Long id) {
        Moment moment = itemService.findMomentById(id, false);
        if (moment == null)
            return ResponseEntity.notFound().build();

        String content = acf.getContent();
        if (content == null || content.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("评论内容不能为空"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NewEntityIdResult(interactionService.addComment(moment, user, content)));
    }

    @PostMapping(path = "/articles/{id}/comments", consumes = "application/json")
    @Authorization
    ResponseEntity<?> addArticleComment(@RequestBody AddCommentForm acf, @CurrentUser User user,
            @PathVariable("id") Long id) {
        Article article = itemService.findArticleById(id, false);
        if (article == null)
            return ResponseEntity.notFound().build();

        String content = acf.getContent();
        if (content == null || content.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("评论内容不能为空"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NewEntityIdResult(interactionService.addComment(article, user, content)));
    }

    @PostMapping(path = "/answers/{id}/comments", consumes = "application/json")
    @Authorization
    ResponseEntity<?> addAnswerComment(@RequestBody AddCommentForm acf, @CurrentUser User user,
            @PathVariable("id") Long id) {
        Answer answer = itemService.findAnswerById(id, false);
        if (answer == null)
            return ResponseEntity.notFound().build();

        String content = acf.getContent();
        if (content == null || content.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("评论内容不能为空"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NewEntityIdResult(interactionService.addComment(answer, user, content)));
    }

    @DeleteMapping("/comments/{id}")
    @Authorization
    ResponseEntity<?> deleteComment(@CurrentUser User user, @PathVariable("id") Long id) {
        User creator = interactionService.getCommentCreator(id);
        if (creator == null)
            return ResponseEntity.notFound().build();
        if (!creator.getId().equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        interactionService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/comments/{id}/report", consumes = "application/json")
    @Authorization
    ResponseEntity<?> doReportComment(@RequestBody ReportForm rf, @CurrentUser User user, @PathVariable("id") Long id) {
        Comment comment = interactionService.findCommentById(id, false);
        return comment == null ? ResponseEntity.notFound().build()
                : ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(
                        interactionService.doReport(comment, user, StringUtil.replaceNull(rf.getReason()))));
    }
}
