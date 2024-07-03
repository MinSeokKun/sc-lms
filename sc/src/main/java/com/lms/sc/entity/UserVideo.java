package com.lms.sc.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserVideo {
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @Column(nullable = false)
    private boolean watched;

    @Column(nullable = false)
    private Instant watchedAt;
    
    @Column
    private Integer watchingTime;
}
