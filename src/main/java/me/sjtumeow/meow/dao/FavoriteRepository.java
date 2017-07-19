package me.sjtumeow.meow.dao;

import java.util.List;

import me.sjtumeow.meow.model.Favorite;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.User;

public interface FavoriteRepository extends SoftDeleteRepository<Favorite, Long> {

    List<Favorite> findByUserAndItem(User user, Item item);

    void deleteByUserAndItem(User user, Item item);
}
