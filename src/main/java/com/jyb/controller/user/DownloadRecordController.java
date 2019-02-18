package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DownloadRecord;
import com.jyb.entity.GameInformation;
import com.jyb.entity.UserInformation;
import com.jyb.init.InitSystem;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserInformationService;
import com.jyb.util.PageUtil;

@Controller
@RequestMapping("user/downloadRecord")
public class DownloadRecordController {

	@Autowired
	private  DownloadRecordService downloadRecordService;
	
	
	@Autowired
	private GameInformationService gameInformationService;
	
	@Autowired
	private UserInformationService userInformationService;
	
	/**
	 *  分页查询用户资源下载信息
	 * @param session
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listPage/{id}")
	public ModelAndView listPage(HttpSession session,@PathVariable(value="id",required=false) Integer page)throws Exception{
		ModelAndView mav=new ModelAndView();
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
		DownloadRecord s_userDownload=new DownloadRecord();
		s_userDownload.setUserInformation(userInformation);
		List<DownloadRecord> userDownLoadList=downloadRecordService.listPage(s_userDownload, page, 10, Sort.Direction.DESC, "downloadDate");
		mav.addObject("userDownLoadList", userDownLoadList);
		Long total=downloadRecordService.getCount(s_userDownload);
		mav.addObject("pageCode", PageUtil.getPagination("/user/downloadRecord/listPage", total, page, 10,""));
		mav.addObject("title", "用户已下载资源页面");
		mav.setViewName("user/personalCenter/userDownloadRecordManagement");
        return mav;
	}
	
	/**
	 * 判断用户是否下载过某资源
	 * @param resourceId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exist")
	public boolean exist(Integer resourceId,String largeCategory,HttpSession session)throws Exception{
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
		Integer count=downloadRecordService.getDownloadTime(userInformation.getId(), resourceId,largeCategory);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断用户积分是否足够
	 * @param userIntegral
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/isIntegralEnough")
	public boolean isIntegralEnough(Integer integral,HttpSession session)throws Exception{
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
		if(userInformation.getUserIntegral()>=integral){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 记录用户下载的资源
	 * @param id
	 * @param type  1.表示用户查看自己发布的资源 2.表示用户已下载过该资源 3.表示其他用户查看别人发布的资源 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDownloadResources/{id}/{type}")
	public synchronized  ModelAndView saveDownloadResources(@PathVariable("id") Integer id,@PathVariable("type") Integer type,HttpServletRequest request) {
		UserInformation userInformation=(UserInformation)request.getSession().getAttribute("userInfo");
		GameInformation gameInfo = gameInformationService.getId(id);
		UserInformation publisher =  gameInfo.getUserInformation();
		DownloadRecord dr = new DownloadRecord();
		ModelAndView mav=new ModelAndView();
		if(type==1){
	    	mav.addObject("gameInfo", gameInfo);
	    	mav.setViewName("common/downloadRecordShow");
		}
		if(type==2){
			mav.addObject("gameInfo", gameInfo);
	    	mav.setViewName("common/downloadRecordShow");
		}
		if(type==3){
			//减去当前登录用户的积分
			userInformation.setUserIntegral(userInformation.getUserIntegral() - gameInfo.getIntegral());
			//给当前资源发布者加积分
			publisher.setUserIntegral(publisher.getUserIntegral()+gameInfo.getIntegral());
			userInformationService.save(userInformation);
			userInformationService.save(publisher);
			dr.setResourceId(id);
			dr.setLargeCategory(gameInfo.getLargeCategory()); 
			dr.setResourceName(gameInfo.getGameName());
			dr.setUserInformation(userInformation);
			dr.setDownloadDate(new Date());
			downloadRecordService.save(dr);
			gameInfo.setGameDownloadFrequency(gameInfo.getGameDownloadFrequency()+1);//增加下载次数
	    	gameInformationService.save(gameInfo);
			mav.addObject("gameInfo", gameInfo);
	    	mav.setViewName("common/downloadRecordShow");
		}
        return mav;
	}
	
	
}
