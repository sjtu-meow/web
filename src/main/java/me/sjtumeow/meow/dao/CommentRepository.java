package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.Comment;

public interface CommentRepository extends SoftDeleteRepository<Comment, Long> {
}
