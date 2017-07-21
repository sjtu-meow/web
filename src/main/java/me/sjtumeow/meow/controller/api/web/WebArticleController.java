package me.sjtumeow.meow.controller.api.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.web.WebAuthUtility;
import me.sjtumeow.meow.model.form.AddArticleForm;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/web/articles")
public class WebArticleController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private WebAuthUtility webAuthUtility;

    @PostMapping(consumes = "application/json")
    ResponseEntity<?> addArticle(HttpSession session, @RequestBody AddArticleForm aaf) {
        String title = aaf.getTitle();
        if (title == null || title.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("文章标题不能为空"));

        String content = aaf.getContent();
        if (content == null || content.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("文章内容不能为空"));

        String cover = aaf.getCover();
        if (cover == null || cover.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("文章缺少封面图"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NewEntityIdResult(itemService.addArticle(aaf, webAuthUtility.getCurrentUser(session))));
    }

    // TODO: Web article editor store/update(?) article

}
