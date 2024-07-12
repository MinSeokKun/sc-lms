package com.lms.sc.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.sc.entity.Note;
import com.lms.sc.entity.Question;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserLecture;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
import com.lms.sc.entity.WeeklyWatchData;
import com.lms.sc.service.NoteService;
import com.lms.sc.service.QuestionService;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;
import com.lms.sc.service.UserVideoService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class UserLectureController {
	private final UserLectureService userLectureService;
	private final UserService userService;
//	private final LectureService lectureService;
	private final VideoService vidService;
	private final UserVideoService userVidService;
	private final QuestionService questService;
	private final NoteService noteService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("list")
	public String getMyList(Principal principal, Model model) throws Exception {
	    if (principal == null) {
	        return "user/login";
	    }
	    
	    SiteUser user = userService.getUserByEmail(principal.getName());
	    List<UserLecture> userLectureList = userLectureService.getMyList(user);
	    model.addAttribute("userLectureList", userLectureList);
	    
	    Map<UserLecture, Map<Integer, Integer>> list = new LinkedHashMap<>();
	    
	    for (UserLecture userLecture : userLectureList) {
	        List<Video> videoList = vidService.VideoList(userLecture.getLecture());
	        int watched = 0;
	        
	        for (Video video : videoList) {
	            UserVideo userVideo = userVidService.getUserVideoOrNew(user, video);
	            if (userVideo.isWatched())
	                watched++;
	        }
	        
	        Map<Integer, Integer> progress = new HashMap<Integer, Integer>();
	        progress.put(videoList.size(), watched);
	        
	        double userLecProgress = (double) watched / videoList.size(); 
	        userLecture = userLectureService.updateProgress(user, userLecture.getLecture(), userLecProgress);
	        
	        list.put(userLecture, progress);
	    }
	    List<UserLecture> updateList = userLectureService.getMyList(user);
	    model.addAttribute("userLecList", updateList);
	    
	    model.addAttribute("list", list);
	    
	    return "mypage/my_list";
	}
	
	//강의 중복 확인
	@PreAuthorize("isAuthenticated()")
	@GetMapping("lecCheck")
	@ResponseBody
	public String lecChk(Principal principal, @RequestParam("lecId") Long lecId) throws Exception {
		String user = principal.getName();
		return userLectureService.checkLec(user, lecId);
	}
	
	
	// 대시보드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("dashboard")
	public String dashboard(Principal principal, Model model,
				@RequestParam(name = "weekOffset", required = false, defaultValue = "0") Integer weekOffset) {
		if (principal == null) {
			return "user/login";
		}
		// 질문 리스트 가져오기
		SiteUser user = userService.getUserByEmail(principal.getName());
		List<Question> questionList = questService.getRecentQuestions(user);
		model.addAttribute("questionList", questionList);
		
		// 노트 리스트 가져오기
		List<Note> noteList = noteService.getRecentNotes(user);
		model.addAttribute("noteList", noteList);
		
		// 노트 리스트 가져오기
		List<UserLecture> userLectureList = userLectureService.getMyList(user);
		model.addAttribute("userLectureList", userLectureList);
		
		// 주간 학습 현황
		WeeklyWatchData weeklyData = userVidService.getWeeklyWatchCount(user, weekOffset);
	    model.addAttribute("weeklyWatchCount", weeklyData.getWatchCount());
	    model.addAttribute("weekDateRange", weeklyData.getDateRange());
	    System.out.println(weeklyData.getDateRange());
	    model.addAttribute("weekOffset", weekOffset != null ? weekOffset : 0);
		
        List<String> recentlyCompletedLectures = userVidService.getRecentlyCompletedLectures(user.getId());
        model.addAttribute("recentlyCompletedLectures", recentlyCompletedLectures);
        
		return "mypage/dashboard";
	}
	
}
