package com.lms.sc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.sc.entity.Answer;
import com.lms.sc.entity.Question;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.repository.AnswerRepository;
import com.lms.sc.repository.QuestionRepository;
import com.lms.sc.repository.UserRepository;
import com.lms.sc.service.UserService;

@SpringBootTest
class ScApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository AnswerRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private UserService userservice;
	
	//@Test
	void userCreate() {
		String username = "민석";
		String email = "minseok";
		String password = "1234";
//		userservice.create(username, email, password);
	}
	
	//@Test
	void test() {
		Question q1 = new Question();
		q1.setTitle("청력");
		q1.setContent("이 안 좋은데 자막기능도 있나요?");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setTitle("시력");
		q2.setContent("이 안 좋은데 강의화면을 확대할 수 있나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}
	
	//@Test
	void test2() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}
	
	
//	@Test
	void test3() {
		Optional<Question> oq = this.questionRepository.findById(53);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		
//		Answer a1 = new Answer();
//		a1.setContent("123.");
//		a1.setQuestion(q);
//		a1.setCreateDate(LocalDateTime.now());
//		this.AnswerRepository.save(a1);
		
		Answer a2 = new Answer();
		a2.setContent("모니터 큰걸로 사용하세요.");
		a2.setQuestion(q);
		a2.setCreateDate(LocalDateTime.now());
		this.AnswerRepository.save(a2);
	}
	//@Test
	void test4() {
		SiteUser u = new SiteUser();
		String name="청력";
		String email="ttt123@naver.com";
		String password="1234";
		String tellNumber="01012345678";
		String profileImage=null;
		userservice.create(name, email, password, tellNumber, profileImage);
	}
	@Test
		void test5() {
			Question q1 = new Question();
			q1.setTitle("아아아");
			q1.setContent("이이dfdfdfdf이이이이");
			q1.setCreateDate(LocalDateTime.now());
			this.questionRepository.save(q1);
	}
}
