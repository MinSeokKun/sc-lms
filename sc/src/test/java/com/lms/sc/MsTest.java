package com.lms.sc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.UserRepository;
import com.lms.sc.repository.VideoRepository;
import com.lms.sc.service.NoteService;

@SpringBootTest
public class MsTest {
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private VideoRepository vr;
	
	@Autowired
	private NoteService ns;
	
	@Test
	void createNote() {
		String content = "";
		long videoTime = 100;
		SiteUser author = ur.findById(1).get();
		Video video = vr.findById(1).get();
		ns.createNote(content, videoTime, author, video);
	}
	
//	@Test
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
