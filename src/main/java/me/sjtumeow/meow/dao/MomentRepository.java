package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Moment;

public interface MomentRepository extends SoftDeleteRepository<Moment, Long> {
	
	List<Moment> findByContentContaining(String keyword);
	
	List<Moment> findByContentContaining(String keyword, Sort sort);
	
	Page<Moment> findByContentContaining(String keyword, Pageable pageable);
	
	List<Moment> findByContentContainingAndDeletedAtIsNull(String keyword);
	
	List<Moment> findByContentContainingAndDeletedAtIsNull(String keyword, Sort sort);
	
	Page<Moment> findByContentContainingAndDeletedAtIsNull(String keyword, Pageable pageable);
	
	List<Moment> findByProfileIdAndDeletedAtIsNull(Long id);
	
	List<Moment> findByProfileIdAndDeletedAtIsNull(Long id, Sort sort);
	
	Page<Moment> findByProfileIdAndDeletedAtIsNull(Long id, Pageable pageable);

}
