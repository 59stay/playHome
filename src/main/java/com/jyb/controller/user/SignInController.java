package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.SignIn;
import com.jyb.entity.UserInformation;
import com.jyb.service.GameInformationService;
import com.jyb.service.SignInService;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.AgentThreadLocal;
import com.jyb.util.CommonMethodUtil;
import com.jyb.util.DateUtil;

@Controller
@RequestMapping("user/SignIn")
public class SignInController {

	@Autowired
	private SignInService signInService;
	
	@Autowired
	private UserInformationService userInformationService;
	
	/**
	 * 加载页面时已签到就展示签到信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadSignIn")
	public Map<String,Object> loadSignIn(HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		 UserInformation userInfo = (UserInformation) session.getAttribute("userInfo");
		 Integer count = CommonMethodUtil.getSignInNumber();
		 if(userInfo!=null){
			 SignIn alreadySignI = CommonMethodUtil.isNoSignIn();
			 map.put("signInNumber", count);//用户签到总人数
			 map.put("alreadySignI", alreadySignI); //已经签到的用户
			 map.put("success",true);
		 }else{
			 map.put("signInNumber", count);//用户签到总人数
			 map.put("success",false); 
		 }
		
    	return map;
    }
	
	
	/**
	 * 记录用户签到
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/loginSignIn")
	public Map<String,Object> loginSignIn(HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		UserInformation userInfo = (UserInformation) session.getAttribute("userInfo");
		if(userInfo==null){
			map.put("success", false);
		}else{
			Integer count = CommonMethodUtil.getSignInNumber();
		    Integer signInRanking  = (count+1);
			SignIn signIn = new SignIn();
			signIn.setUserInformation(userInfo);
			Date currentDate  = new Date();
			String  strDate = DateUtil.formatDate(currentDate, "yyyy-MM-dd HH:mm:ss");
			signIn.setSignInRanking(signInRanking);//签到排名
			signIn.setSignInTime(strDate);//签到时间
			signInService.save(signIn);
			UserInformation userInformation  = userInformationService.getById(userInfo.getId());
			userInformation.setUserIntegral(userInformation.getUserIntegral()+3);//签到成功加3积分
			map.put("signInNumber", signInRanking);//用户签到总人数
			map.put("alreadySignI", signIn); //已经签到的用户信息
			map.put("success", true);
		}
		return map;
	}
}
