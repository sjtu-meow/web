package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Comment;

public interface CommentRepository extends SoftDeleteRepository<Comment, Long> {

    List<Comment> findByContentContaining(String keyword);

    List<Comment> findByContentContaining(String keyword, Sort sort);

    Page<Comment> findByContentContaining(String keyword, Pageable pageable);
}
