package com.jyb.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
	private ModelAndView root() {
	    ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 后台-首页
	 */
	
	@RequestMapping("admin/index")
	@RequiresPermissions(value={"后台-首页"})
	private ModelAndView rootAdmin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/index");
		return mv;
	}

	/**
	 * 后台-游戏资源
	 */
	
	@RequestMapping("admin/gameResource")
	@RequiresPermissions(value={"后台-游戏资源"})
	private ModelAndView gameResource() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/game/page/gameResource");
		return mv;
	}
	
	/**
	 * 后台-软件资源
	 */
	@RequestMapping("admin/softwareResource")
	@RequiresPermissions(value={"后台-游戏资源"})
	private ModelAndView softwareResource() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/software/page/softwareResource");
		return mv;
	}
	
	
	/**
	 * 后台-用户信息
	 */
	
	@RequestMapping("admin/userInfo")
	@RequiresPermissions(value={"后台-用户信息"})
	private ModelAndView userInfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/userInformation");
		return mv;
	}

	/**
	 * 后台-类别信息
	 */
	@RequestMapping("admin/dataDictionary")
	@RequiresPermissions(value={"后台-类别信息"})
	private ModelAndView dataDictionary() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/dataDictionary");
		return mv;
	}

	/**
	 * 后台-友情链接
	 */
	@RequestMapping("admin/friendshipLink")
	@RequiresPermissions(value={"后台-友情链接"})
	private ModelAndView friendshipLink() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/friendshipLink");
		return mv;
	}
	/**
	 * 后台-留言板
	 */
	@RequestMapping("admin/userMessage")
	@RequiresPermissions(value={"后台-留言板"})
	private ModelAndView userMessage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/userMessage");
		return mv;
	}
	
	/**
	 * 后台-失效链接
	 */

	@RequestMapping("admin/invalidLink")
	@RequiresPermissions(value={"后台-失效链接"})
	private ModelAndView invalidLink() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/invalidLink");
		return mv;
	}
	
	/**
	 * 后台-日志信息
	 */

	@RequestMapping("admin/logInfo")
	@RequiresPermissions(value={"后台-日志信息"})
	private ModelAndView logInfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/logInfo");
		return mv;
	}
	
	/**
	 * 后台-评论信息
	 */
	
	@RequestMapping("admin/userReviews")
	@RequiresPermissions(value={"后台-评论信息"})
	private ModelAndView userReviews() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/page/userReviews");
		return mv;
	}
}
