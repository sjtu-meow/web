package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.PushArchive;

public interface PushService {

    Iterable<PushArchive> findAll(String keyword);

    Iterable<PushArchive> findAllPageable(Integer page, Integer size, String keyword);

    Long create(Item item, String text);
}
