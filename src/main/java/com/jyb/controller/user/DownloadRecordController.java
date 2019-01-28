package com.jyb.controller.user;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DownloadRecord;
import com.jyb.entity.GameInformation;
import com.jyb.entity.UserInformation;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserInformationService;

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
	 * 
	 * @param id
	 * @param type  1.表示用户查看自己发布的资源 2.表示用户已下载过该资源 3.表示其他用户查看别人发布的资源 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveAndShowDownloadRecord/{id}/{type}")
	@Transactional
	public synchronized  ModelAndView saveAndShowDownloadRecord(@PathVariable("id") Integer id,@PathVariable("type") Integer type,HttpSession session) {
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
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
			mav.addObject("gameInfo", gameInfo);
	    	mav.setViewName("common/downloadRecordShow");
		}
		
        return mav;
	}
	
	
}
