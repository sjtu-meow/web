package me.sjtumeow.meow.service;

import java.util.List;

import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.result.BaseSummaryResult;

public interface InteractionService {
	
	// Favorite
	
	List<BaseSummaryResult> getUserFavorites(User user);
	
	boolean checkFavorite(User user, Item item);
	
	void doFavorite(User user, Item item);
	
	void cancelFavorite(User user, Item item);
}
