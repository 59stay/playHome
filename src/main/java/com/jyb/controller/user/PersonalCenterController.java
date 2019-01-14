package com.jyb.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("personalCenter")
public class PersonalCenterController {
	/**
	 * 用户中心-main页
	 * @param session
	 * @return
	 */
    @RequestMapping("toMain")
    public ModelAndView toPersonalCenterMain(HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("user/personalCenter/main");
        return mav;
    }
    
    /**
	 * 用户中心-发布资源
	 * @return
	 */
    @RequestMapping("/toReleaseResource")
    public ModelAndView toPublishArticlePage(){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("user/personalCenter/publishingResources");
        return mav;
    }
    
}
