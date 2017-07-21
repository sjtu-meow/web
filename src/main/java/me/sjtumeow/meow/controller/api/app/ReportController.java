package me.sjtumeow.meow.controller.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ReportForm;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.InteractionService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping(consumes = "application/json")
    @Authorization
    ResponseEntity<?> doReport(@RequestBody ReportForm rf, @CurrentUser User user) {
        CreateResult cr = interactionService.doReport(rf, user);
        return cr.isResult() ? ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(cr.getId()))
                : ResponseEntity.badRequest().body(new FailureMessageResult(cr.getMessage()));
    }
}
