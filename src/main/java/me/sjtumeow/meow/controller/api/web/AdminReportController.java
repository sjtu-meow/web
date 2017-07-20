package me.sjtumeow.meow.controller.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Report;
import me.sjtumeow.meow.model.form.UpdateReportForm;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.util.FormatValidator;
import me.sjtumeow.meow.util.StringUtil;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    @Autowired
    private InteractionService interactionService;

    @GetMapping("/moments")
    Iterable<Report> getMomentReports(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String status) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllReports(Item.ITEM_TYPE_MOMENT, StringUtil.replaceNull(status))
                : interactionService.findAllReportsPageable(page, size, Item.ITEM_TYPE_MOMENT,
                        StringUtil.replaceNull(status));
    }

    @GetMapping("/articles")
    Iterable<Report> getArticleReports(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String status) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllReports(Item.ITEM_TYPE_ARTICLE, StringUtil.replaceNull(status))
                : interactionService.findAllReportsPageable(page, size, Item.ITEM_TYPE_ARTICLE,
                        StringUtil.replaceNull(status));
    }

    @GetMapping("/questions")
    Iterable<Report> getQuestionReports(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String status) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllReports(Item.ITEM_TYPE_QUESTION, StringUtil.replaceNull(status))
                : interactionService.findAllReportsPageable(page, size, Item.ITEM_TYPE_QUESTION,
                        StringUtil.replaceNull(status));
    }

    @GetMapping("/answers")
    Iterable<Report> getAnswerReports(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String status) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllReports(Item.ITEM_TYPE_ANSWER, StringUtil.replaceNull(status))
                : interactionService.findAllReportsPageable(page, size, Item.ITEM_TYPE_ANSWER,
                        StringUtil.replaceNull(status));
    }

    @GetMapping("/comments")
    Iterable<Report> getCommentReports(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size, @RequestParam(required = false) String status) {
        return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size))
                ? interactionService.findAllReports(Item.ITEM_TYPE_COMMENT, StringUtil.replaceNull(status))
                : interactionService.findAllReportsPageable(page, size, Item.ITEM_TYPE_COMMENT,
                        StringUtil.replaceNull(status));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    ResponseEntity<?> updateReport(@RequestBody UpdateReportForm urf, @PathVariable("id") Long id) {
        return interactionService.updateReport(id, urf) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
