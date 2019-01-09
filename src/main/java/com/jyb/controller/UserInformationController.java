package com.jyb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.util.CryptographyUtil;
import com.jyb.util.IpUtil;
import com.jyb.util.MD5Util;
/**
 * 用户信息控制层
 * @author jyb
 *
 */
@Controller
@RequestMapping("userInformation")
public class UserInformationController {
	
	@Autowired
	private UserInformationService userInformationService;
	
	/**
	 * 用户注册
	 * @param userInformation
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("register")
	@ResponseBody
	public Map<String,Object> registerUserInformation(@Valid UserInformation userInformation,BindingResult bindingResult,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		if(bindingResult.hasErrors()){
			map.put("success",false);
			map.put("errorInfo",bindingResult.getFieldError().getDefaultMessage());
		}else if(userInformationService.checkUserName(userInformation.getUserName())!=null){
			map.put("success",false);
			map.put("errorInfo","用户名已存在，请更换");
		}else if(userInformationService.checkEmail(userInformation.getEmail())!=null){
			map.put("success",false);
			map.put("errorInfo","邮箱已存在，请更换");
		}else{
			
			userInformation.setUserPassword(CryptographyUtil.md5(userInformation.getUserPassword(),CryptographyUtil.SALT));
			userInformation.setUserCreationTime(new Date());
			userInformation.setUserHead("default.jpg");
			userInformation.setHostIp(IpUtil.getIpAddr(request));
			userInformationService.save(userInformation);
			map.put("success",true);
		}
		return map;
	}
	
}
