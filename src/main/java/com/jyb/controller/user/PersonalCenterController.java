package com.jyb.controller.user;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.InvalidLink;
import com.jyb.entity.UserInformation;
import com.jyb.service.GameInformationService;
import com.jyb.service.InvalidLinkService;
import com.jyb.service.SoftwareService;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.Constant;


@Controller
@RequestMapping("personalCenter")
public class PersonalCenterController {
	
	@Autowired
	private GameInformationService gameInformationService;
	
	@Autowired
	private InvalidLinkService invalidLinkService;
	
	@Autowired
	private UserInformationService userInformationService;

	@Autowired
	private SoftwareService softwareService;
	/**
	 * 个人中心-main页
	 * @param session
	 * @return
	 */
    @RequestMapping("toMain")
    public ModelAndView toPersonalCenterMain(HttpSession session){
    	ModelAndView mv=new ModelAndView();
    	UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
    	InvalidLink  invalidLink = new InvalidLink();
    	invalidLink.setUserId(userInformation.getId());
    	Long count=invalidLinkService.getCount(invalidLink);
    	session.setAttribute("invalidLinkCount", count);
    	mv.setViewName("user/personalCenter/main");
        return mv;
    }
    
    /**
	 * 个人中心-个人资料
	 * @param session
	 * @return
	 */
    @RequestMapping("personalData")
    public ModelAndView personalData(HttpSession session){
    	ModelAndView mv=new ModelAndView();
    	UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
    	UserInformation userInfo = userInformationService.getById(userInformation.getId());
    	mv.addObject("userInformation", userInfo);
    	mv.setViewName("user/personalCenter/personalData");
        return mv;
    }
    
    /**
     *  个人中心-用户发布资源信息
     * @return
     */
    @RequestMapping("/toPublishingResources")
    private ModelAndView  toPublishingResources(){
       ModelAndView mv  =  new ModelAndView();
       mv.addObject("title","发布资源信息");
       mv.setViewName("user/personalCenter/publishingResources");
       return mv;
    }
    
    
    /**
	 * 个人中心-发布游戏资源
	 * @return
	 */
    @RequestMapping("/toPublishGameResources")
    private ModelAndView toPublishGameResources(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "发布游戏资源信息页面");
    	mav.setViewName("user/game/publishGameResource");
        return mav;
    }
    
    /**
     * 个人中心-修改游戏资源
     * @return
     */
    @RequestMapping("/toModifyGameResource/{id}")
    public ModelAndView toModifyGameResource(@PathVariable("id") Integer id){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "修游戏资源信息页面");
    	mav.addObject("gameInformation", gameInformationService.getId(id));
    	mav.setViewName("user/game/modifyGameResource");
        return mav;
    }
    
    /**
     * 个人中心-修改软件资源
     * @return
     */
    @RequestMapping("/toModifySoftware/{id}")
    public ModelAndView toModifySoftware(@PathVariable("id") Integer id){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "修软件资源信息页面");
    	mav.addObject("software", softwareService.getId(id));
    	mav.setViewName("user/software/modifySoftware");
        return mav;
    } 
    
    /**
     * 个人中心-游戏资源管理
     * @return
     */
    @RequestMapping("/toGameResourceManagement")
    private ModelAndView toGameResourceManagement(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "游戏资源信息管理页面");
    	mav.setViewName("user/game/gameResourceManagement");
        return mav;
    }
    
    
    /**
     * 个人中心-软件资源管理
     * @return
     */
    @RequestMapping("/toSoftwareManagement")
    private ModelAndView toSoftwareManagement(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "软件资源信息管理页面");
    	mav.setViewName("user/software/softwareManagement");
        return mav;
    }
    
    
    /**
     * 个人中心-失效链接管理
     * @return
     */
    @RequestMapping("/toInvalidLinkManagement")
    private ModelAndView toInvalidLinkManagement(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "失效链接信息管理页面");
    	mav.setViewName("user/personalCenter/invalidLinkManagement");
        return mav;
    }
    /**
     *  个人中心-用户评论管理
     * @return
     */
    @RequestMapping("/toUserReviewsManagement")
    private ModelAndView  toUserReviewsManagement(){
       ModelAndView mv  =  new ModelAndView();
       mv.addObject("title","用户评论管理页面");
       mv.setViewName("user/personalCenter/userReviewsManagement");
       return mv;
    }
	
    
    /**
     *  个人中心-用户已下载资源信息
     * @return
     */
    @RequestMapping("/toUserDownloadRecordManagement")
    private ModelAndView  toUserDownloadRecordManagement(){
       ModelAndView mv  =  new ModelAndView();
       mv.addObject("title","用户已下载资源信息");
       mv.setViewName("user/personalCenter/userDownloadRecordManagement");
       return mv;
    }
    
    
    
}
