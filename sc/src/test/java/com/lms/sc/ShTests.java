package com.lms.sc;



import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.sc.entity.LecVideo;
import com.lms.sc.entity.Lecture;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.LecvideoRepository;

@SpringBootTest
class ShTests {

	@Autowired
	private LectureRepository lr;
	
	@Autowired
	private LecvideoRepository lvr;
	
	//@Test
	void lectureTest() {
		Lecture lec = new Lecture();
		lec.setTitle("자바 강의");
		lec.setContent("자바을 잘 배워볼까요~?");
		lec.setCreateDate(LocalDateTime.now());
		
		
        System.out.println("@@@@@@@@@@@Saved Lecture: " + lr.save(lec));
	}
	
	@Test
	void lecVideoTest() {
		Lecture lecture = lr.findById(1L).orElse(null);
		LecVideo lecVideo = new LecVideo();
		lecVideo.setTitle("자바");
		lecVideo.setUrl("123123123");
		lecVideo.setLecture(lecture);
		
		lvr.save(lecVideo);
	}

}
