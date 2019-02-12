package com.jyb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.GameInformation;
import com.jyb.service.GameInformationService;

@Controller
public class IndexController {
	@Autowired
	private GameInformationService gameInformationService;

	/**
	 * 网站根目录请求
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView root() {
	    ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 后台-首页
	 */
	@RequestMapping("admin/index")
	public ModelAndView rootAdmin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/index");
		return mv;
	}

	/**
	 * 后台-游戏资源
	 */
	@RequestMapping("admin/gameResource")
	public ModelAndView gameResource() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/game/page/gameResource");
		return mv;
	}

	/**
	 * 后台-用户信息
	 */
	@RequestMapping("admin/userInfo")
	public ModelAndView userInfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/userInformation");
		return mv;
	}

	/**
	 * 后台-类别信息
	 */
	@RequestMapping("admin/dataDictionary")
	public ModelAndView dataDictionary() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/dataDictionary");
		return mv;
	}

	/**
	 * 后台-友情链接
	 */
	@RequestMapping("admin/friendshipLink")
	public ModelAndView friendshipLink() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/friendshipLink");
		return mv;
	}
	/**
	 * 后台-留言板
	 */
	@RequestMapping("admin/userMessage")
	public ModelAndView userMessage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/userMessage");
		return mv;
	}
	
	/**
	 * 后台-失效链接
	 */
	@RequestMapping("admin/invalidLink")
	public ModelAndView invalidLink() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/invalidLink");
		return mv;
	}
	
	
}
