package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.PushArchive;

public interface PushArchiveRepository extends SoftDeleteRepository<PushArchive, Long> {

    List<PushArchive> findByTextContaining(String keyword);

    List<PushArchive> findByTextContaining(String keyword, Sort sort);

    Page<PushArchive> findByTextContaining(String keyword, Pageable pageable);
}
