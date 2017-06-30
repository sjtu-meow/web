package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;

public interface ItemService {
	
	Iterable<Moment> findAllMoments(Integer page, Integer size);
	
	Moment findMomentById(Long id);
	
	User getMomentCreator(Long id);
	
	boolean deleteMoment(Long id);
}
