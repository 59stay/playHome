package com.jyb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController {
	/**
	 * 网站根目录请求
	 * @return
	 */
    @RequestMapping("/")
    public ModelAndView root(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("index");
    	return mv;
    }
    
    /**
     * 
     * @param session
     * @return
     */
    @RequestMapping("user/personalCenter")
    public ModelAndView toUserCenterPage(HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "用户中心页面");
    	mav.setViewName("user/personalCenter");
        return mav;
    }
     
    
    
}
