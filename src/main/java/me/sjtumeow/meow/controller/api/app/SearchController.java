package me.sjtumeow.meow.controller.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.service.ItemService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            return ResponseEntity.badRequest().body(new FailureMessageResult("搜索关键词不能为空"));

        return ResponseEntity.ok(itemService.comprehensiveSearch(keyword));
    }
}
