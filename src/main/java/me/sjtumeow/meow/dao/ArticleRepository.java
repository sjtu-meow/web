package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Article;

public interface ArticleRepository extends SoftDeleteRepository<Article, Long> {
	
	List<Article> findByTitleContaining(String keyword);
	
	List<Article> findByTitleContaining(String keyword, Sort sort);
	
	Page<Article> findByTitleContaining(String keyword, Pageable pageable);
	
	List<Article> findByTitleContainingAndDeletedAtIsNull(String keyword);
	
	List<Article> findByTitleContainingAndDeletedAtIsNull(String keyword, Sort sort);
	
	Page<Article> findByTitleContainingAndDeletedAtIsNull(String keyword, Pageable pageable);
}
