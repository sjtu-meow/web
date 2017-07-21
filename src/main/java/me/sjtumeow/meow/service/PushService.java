package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.model.form.PushForm;
import me.sjtumeow.meow.model.result.CreateResult;

public interface PushService {

    Iterable<PushArchive> findAll(String keyword);

    Iterable<PushArchive> findAllPageable(Integer page, Integer size, String keyword);

    CreateResult create(PushForm pf);
}
