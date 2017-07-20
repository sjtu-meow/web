package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.service.InteractionService;

@RestController
@RequestMapping("/api/admin/comments")
public class AdminCommentController {

    @Autowired
    private InteractionService interactionService;

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        return interactionService.deleteComment(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
