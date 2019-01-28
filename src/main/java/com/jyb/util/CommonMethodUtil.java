package com.jyb.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jyb.entity.SignIn;
import com.jyb.entity.UserInformation;
import com.jyb.service.SignInService;
import com.jyb.specialEntity.AgentThreadLocal;
/**
 * 公共方法返回调用值
 * @author jyb
 *
 */
@Component 
public class CommonMethodUtil {

	@Autowired
	private static SignInService signInService;
	
	@Autowired(required = true)
	public  void setSignInService(SignInService signInService) {
		CommonMethodUtil.signInService = signInService;
	}


	/**
	 * 返回当天签到的总人数
	 * @return
	 */
   
	public static  Integer getSignInNumber(){
		SignIn sI = new SignIn();
		Date currentDate  = new Date();
		String  strDate = DateUtil.formatDate(currentDate, "yyyy-MM-dd HH:mm:ss");
		String subStr=strDate.substring(0, 10);
		sI.setSignInTime(subStr);
		Long count = signInService.getCount(sI);
		Integer signInNumber   = count.intValue();
		return signInNumber;
	}
	/**
	 * 判断用户当天是否签过到
	 * @return
	 */
	public static  SignIn isNoSignIn(){
	    UserInformation userInfo = 	AgentThreadLocal.get();
	    SignIn signIn = new SignIn();
	    signIn.setUserInformation(userInfo);
		Date currentDate  = new Date();
		String  strDate = DateUtil.formatDate(currentDate, "yyyy-MM-dd HH:mm:ss");
		String subStr=strDate.substring(0, 10);
		signIn.setSignInTime(subStr);
		SignIn alreadySignI = signInService.getUserId(signIn.getUserInformation().getId(), signIn.getSignInTime());
		return alreadySignI;
	}
	
	
}
