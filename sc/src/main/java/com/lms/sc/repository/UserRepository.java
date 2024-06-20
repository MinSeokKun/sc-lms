package com.lms.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.SiteUser;


public interface UserRepository extends JpaRepository<SiteUser, Long> {
	SiteUser findByEmail(String email);
}
