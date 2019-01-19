package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserReviewsService;

@Controller
@RequestMapping("user/userReviews")
public class UserReviewsController {

	@Autowired
	private UserReviewsService userReviewsService;
	
	@Autowired
	private GameInformationService gameInformationService;
	
	
	@ResponseBody
    @PostMapping("/add")
	public Map<String,Object> addGameInformation(UserReviews userReviews,HttpSession session){
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute("sessionUserInformation");
		userReviews.setReviewsTime(new Date());
		userReviews.setUserInformation(userInformation);
		userReviewsService.save(userReviews);
		map.put("success", true);
		return map;
	}
}
