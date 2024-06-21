package com.lms.sc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.sc.entity.Note;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.repository.NoteRepository;
import com.lms.sc.repository.UserRepository;

@SpringBootTest
public class MsTest {
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private NoteRepository nr;
	
	@Test
	void createUser() {
		SiteUser user = new SiteUser();
		user.setName("민석");
		user.setEmail("minseok@naver.com");
		user.setPassword("1234");
		user.setTellNumber("01051567115");
		ur.save(user);
	}
	
//	@Test
//	void createNote() {
//		String email = "minseok@naver.com";
//		SiteUser user = ur.findByEmail(email);
//		Note note = new Note();
//		note.setAuthor(user);
//		note.setContent("민석 노트 인설트 테스트");
//		nr.save(note);
//	}
}
