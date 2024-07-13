package com.lms.sc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Notice;
import com.lms.sc.entity.Question;
import com.lms.sc.entity.SiteUser;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	Page<Notice> findAll(Pageable pagealble);
	
	List<Notice> findByAuthor(SiteUser author);
}
