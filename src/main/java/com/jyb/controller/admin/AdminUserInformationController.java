package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("admin/userInfor")
public class AdminUserInformationController {
	
	@Autowired
	private UserInformationService userInformationService;
	
	/**
	 * 用户登录
	 * @param userInformation
	 * @param session
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> loginUserInformation(UserInformation userInformation,String code,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		if(userInformation!=null &&(StringUtil.isNotEmpty(code) )){
			if(userInformation.getUserName().equals("admin")){
				UserInformation sessionUserInformation = userInformationService.findByUserName(userInformation.getUserName());
				request.getSession().setAttribute("userInfo", sessionUserInformation);
				map.put("success",true);
			}else{
				map.put("success",false);
				map.put("errorInfo","登录失败，账号或者密码错误");
			}
		}else{
			map.put("success",false);
			map.put("errorInfo","登录失败，账号或者密码错误");
		}
		return map;
	}
	
	
}
