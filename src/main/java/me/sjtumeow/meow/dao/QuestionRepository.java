package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Question;

public interface QuestionRepository extends SoftDeleteRepository<Question, Long> {
	
	List<Question> findByTitleContaining(String keyword);
	
	List<Question> findByTitleContaining(String keyword, Sort sort);
	
	Page<Question> findByTitleContaining(String keyword, Pageable pageable);
	
	List<Question> findByTitleContainingAndDeletedAtIsNull(String keyword);
	
	List<Question> findByTitleContainingAndDeletedAtIsNull(String keyword, Sort sort);
	
	Page<Question> findByTitleContainingAndDeletedAtIsNull(String keyword, Pageable pageable);
	
}
