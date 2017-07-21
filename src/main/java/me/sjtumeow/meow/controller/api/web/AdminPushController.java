package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.model.form.PushForm;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.PushService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/pushes")
public class AdminPushController {

    @Autowired
    private PushService pushService;

    @GetMapping
    Iterable<PushArchive> getPushArchives(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String keyword) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? pushService.findAll(StringUtil.replaceNull(keyword))
                : pushService.findAllPageable(page, size, StringUtil.replaceNull(keyword));
    }

    @PostMapping(consumes = "application/json")
    ResponseEntity<?> createPush(@RequestBody PushForm pf) {
        CreateResult cr = pushService.create(pf);
        return cr.isResult() ? ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(cr.getId()))
                : ResponseEntity.badRequest().body(new FailureMessageResult(cr.getMessage()));
    }

}
