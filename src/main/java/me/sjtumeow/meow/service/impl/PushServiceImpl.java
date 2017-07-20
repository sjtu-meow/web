package me.sjtumeow.meow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import me.sjtumeow.meow.dao.PushArchiveRepository;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.service.PushService;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private PushArchiveRepository pushArchiveRepository;

    public Iterable<PushArchive> findAll(String keyword) {
        return pushArchiveRepository.findByTextContaining(keyword, new Sort(Direction.DESC, "createdAt"));
    }

    public Iterable<PushArchive> findAllPageable(Integer page, Integer size, String keyword) {
        return pushArchiveRepository.findByTextContaining(keyword,
                new PageRequest(page, size, Direction.DESC, "createdAt"));
    }

    public Long create(Item item, String text) {

        // Call leanCloud Push API here...

        PushArchive pushArchive = new PushArchive(item, text);
        pushArchiveRepository.save(pushArchive);
        return pushArchive.getId();
    }
}
