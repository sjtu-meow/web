package me.sjtumeow.meow.controller.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/home")
public class AppHomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @Authorization
    Iterable<BaseSummaryResult> getHomePageContents(@CurrentUser User user) {
        return itemService.getHomePageContents(user);
    }
}
