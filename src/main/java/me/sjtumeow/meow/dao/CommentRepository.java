package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends SoftDeleteRepository<Comment, Long> {
}
