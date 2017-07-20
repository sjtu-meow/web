package me.sjtumeow.meow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.sjtumeow.meow.model.Report;

public interface ReportRepository extends SoftDeleteRepository<Report, Long> {

    List<Report> findByItemType(Integer type);

    List<Report> findByItemType(Integer type, Sort sort);

    Page<Report> findByItemType(Integer type, Pageable pageable);

    List<Report> findByItemTypeAndClosed(Integer type, Boolean closed);

    List<Report> findByItemTypeAndClosed(Integer type, Boolean closed, Sort sort);

    Page<Report> findByItemTypeAndClosed(Integer type, Boolean closed, Pageable pageable);
}
