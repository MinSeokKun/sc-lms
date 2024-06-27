package com.lms.sc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.SiteUser;



public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByEmail(String email);
	Optional<SiteUser> findById(long id);
	Optional<SiteUser> findByName(String name);
}
