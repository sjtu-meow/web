package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Moment;

public interface ItemService {

	Iterable<Moment> getMoments(Integer page, Integer size);
}
