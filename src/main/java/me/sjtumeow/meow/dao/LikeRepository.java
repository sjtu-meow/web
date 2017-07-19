package me.sjtumeow.meow.dao;

import java.util.List;

import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.Like;
import me.sjtumeow.meow.model.User;

public interface LikeRepository extends SoftDeleteRepository<Like, Long> {

    List<Like> findByUserAndItem(User user, Item item);

    void deleteByUserAndItem(User user, Item item);
}
