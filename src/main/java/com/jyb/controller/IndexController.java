package com.jyb.controller;


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
    
   
     
    
    
}
