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


@Controller
@RequestMapping("personalCenter")
public class PersonalCenterController {
	
	@Autowired
	private GameInformationService gameInformationService;
	
	@Autowired
	private InvalidLinkService invalidLinkService;
	
	
	/**
	 * 个人中心-main页
	 * @param session
	 * @return
	 */
    @RequestMapping("toMain")
    public ModelAndView toPersonalCenterMain(HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
    	InvalidLink  invalidLink = new InvalidLink();
    	invalidLink.setUserId(userInformation.getId());
    	Long count=invalidLinkService.getCount(invalidLink);
    	session.setAttribute("invalidLinkCount", count);
    	mav.setViewName("user/personalCenter/main");
        return mav;
    }
    
    /**
	 * 个人中心-发布游戏资源
	 * @return
	 */
    @RequestMapping("/toPublishGameResources")
    public ModelAndView toPublishGameResources(){
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
     * 个人中心-游戏资源管理
     * @return
     */
    @RequestMapping("/toGameResourceManagement")
    public ModelAndView toGameResourceManagement(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "游戏资源信息管理页面");
    	mav.setViewName("user/game/gameResourceManagement");
        return mav;
    }
    
    /**
     * 个人中心-失效链接管理
     * @return
     */
    @RequestMapping("/toInvalidLinkManagement")
    public ModelAndView toInvalidLinkManagement(){
    	ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "失效链接信息管理页面");
    	mav.setViewName("user/personalCenter/InvalidLinkManagement");
        return mav;
    }
    /**
     *  个人中心-用户评论管理
     * @return
     */
    @RequestMapping("/toUserReviewsManagement")
    public ModelAndView  toUserReviewsManagement(){
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
    public ModelAndView  toUserDownloadRecordManagement(){
       ModelAndView mv  =  new ModelAndView();
       mv.addObject("title","用户已下载资源信息");
       mv.setViewName("user/personalCenter/userDownloadRecordManagement");
       return mv;
    }
    
}
