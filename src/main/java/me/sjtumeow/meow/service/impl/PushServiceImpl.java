package me.sjtumeow.meow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.PushArchiveRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.model.form.PushForm;
import me.sjtumeow.meow.model.result.CreateResult;
import me.sjtumeow.meow.service.PushService;
import me.sjtumeow.meow.util.FormatValidator;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private MomentRepository momentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PushArchiveRepository pushArchiveRepository;

    public Iterable<PushArchive> findAll(String keyword) {
        return pushArchiveRepository.findByTextContaining(keyword, new Sort(Direction.DESC, "createdAt"));
    }

    public Iterable<PushArchive> findAllPageable(Integer page, Integer size, String keyword) {
        return pushArchiveRepository.findByTextContaining(keyword,
                new PageRequest(page, size, Direction.DESC, "createdAt"));
    }

    public CreateResult create(PushForm pf) {
        Long itemId = pf.getItemId();
        if (itemId == null)
            return new CreateResult("缺失项目编号");

        Integer itemType = pf.getItemType();
        if (!FormatValidator.checkMainItemType(itemType))
            return new CreateResult("非法的项目类型");

        Item item;

        switch (itemType) {
            case Item.ITEM_TYPE_MOMENT:
                item = momentRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的点滴不存在");
                break;
            case Item.ITEM_TYPE_ARTICLE:
                item = articleRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的文章不存在");
                break;
            case Item.ITEM_TYPE_QUESTION:
                item = questionRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的问题不存在");
                break;
            case Item.ITEM_TYPE_ANSWER:
                item = answerRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的回答不存在");
                break;
            default:
                item = commentRepository.findOneActive(itemId);
                if (item == null)
                    return new CreateResult("编号为 " + itemId + " 的评论不存在");
                break;
        }

        String text = pf.getText();
        if (text == null || text.trim().isEmpty())
            return new CreateResult("文字不能为空");

        // Call leanCloud Push API here...

        PushArchive pushArchive = new PushArchive(item, text);
        pushArchiveRepository.save(pushArchive);
        return new CreateResult(pushArchive.getId());
    }
}
