package com.lms.sc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class LecVideo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vidId;
	
	@Column(columnDefinition = "TEXT")
	private String vidUrl;
	
	@Column(length = 200)
	private String vidTit;
	
	//지연 로딩(Lazy Loading) 전략을 사용하여 Lecture 엔티티를 필요할 때만 로드
	@ManyToOne(fetch = FetchType.LAZY)
	//LecVideo 테이블의 lecId 컬럼이 Lecture 테이블의 기본 키를 참조하는 외래 키라는 것을 의미
    @JoinColumn(name = "lecId")
	private long lecId;
}
