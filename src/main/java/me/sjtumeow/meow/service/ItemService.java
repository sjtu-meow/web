package me.sjtumeow.meow.service;

import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;

public interface ItemService {
	
	Iterable<Moment> findAllMoments(Integer page, Integer size);
	
	Moment findMomentById(Long id);
	
	User getMomentCreator(Long id);
	
	boolean addMoment(AddMomentForm amf, User user);
	
	boolean deleteMoment(Long id);
}
