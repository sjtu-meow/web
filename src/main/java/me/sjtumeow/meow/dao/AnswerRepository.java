package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Answer;

public interface AnswerRepository extends SoftDeleteRepository<Answer, Long> {
	
	List<Answer> findByContentContaining(String keyword);
	
	List<Answer> findByContentContaining(String keyword, Sort sort);
	
	Page<Answer> findByContentContaining(String keyword, Pageable pageable);
	
	List<Answer> findByContentContainingAndDeletedAtIsNull(String keyword);
	
	List<Answer> findByContentContainingAndDeletedAtIsNull(String keyword, Sort sort);
	
	Page<Answer> findByContentContainingAndDeletedAtIsNull(String keyword, Pageable pageable);
	
}
