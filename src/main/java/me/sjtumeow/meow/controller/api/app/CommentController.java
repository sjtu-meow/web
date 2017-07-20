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
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddCommentForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;

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
}
