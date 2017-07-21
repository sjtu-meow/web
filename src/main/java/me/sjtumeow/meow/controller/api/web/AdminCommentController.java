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

import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.form.UpdateCommentForm;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/comments")
public class AdminCommentController {

    @Autowired
    private InteractionService interactionService;

    @GetMapping
    Iterable<Comment> getComments(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String keyword) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllComments(StringUtil.replaceNull(keyword))
                : interactionService.findAllCommentsPageable(page, size, StringUtil.replaceNull(keyword));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getComment(@PathVariable("id") Long id) {
        Comment comment = interactionService.findCommentById(id, true);
        return comment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(comment);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> updateComment(@RequestBody UpdateCommentForm ucf, @PathVariable("id") Long id) {
        return interactionService.updateComment(id, ucf) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        return interactionService.deleteComment(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
