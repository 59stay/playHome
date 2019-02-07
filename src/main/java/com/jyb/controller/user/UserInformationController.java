package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.CryptographyUtil;
import com.jyb.util.IpUtil;
import com.jyb.util.StringUtil;
/**
 * 用户信息控制层
 * @author jyb
 *
 */
@Controller
@RequestMapping("user/userInformation")
public class UserInformationController {
	
	@Autowired
	private UserInformationService userInformationService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * 用户登录
	 * @param userInformation
	 * @param session
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> loginUserInformation(UserInformation userInformation,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		if(userInformation!=null ){
			if(userInformation.getUserName().equals("jyb") || userInformation.getUserName().equals("test1") || userInformation.getUserName().equals("test2")|| userInformation.getUserName().equals("test3")  ){
				UserInformation sessionUserInformation = userInformationService.findByUserName(userInformation.getUserName());
				request.getSession().setAttribute("userInfo", sessionUserInformation);
				map.put("success",true);
			}else{
				map.put("success",false);
				map.put("errorInfo","登录失败，账号或者密码错误");
			}
		}
		return map;
	}
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
		}else if(userInformationService.findByUserName(userInformation.getUserName())!=null){
			map.put("success",false);
			map.put("errorInfo","用户名已存在,请更换");
		}else if(userInformationService.findByEmail(userInformation.getEmail())!=null){
			map.put("success",false);
			map.put("errorInfo","邮箱已存在,请更换");
		}else{
			userInformation.setUserPassword(CryptographyUtil.md5(userInformation.getUserPassword(),Constant.SALT));
			userInformation.setUserCreationTime(new Date());
			userInformation.setUserHead("default.jpg");
			userInformation.setAccountStatus(0);
			userInformation.setUserIntegral(0);
			userInformation.setUserRole(1);
			userInformation.setHostIp(IpUtil.getIpAddr(request));
			userInformationService.save(userInformation);
			map.put("success",true);
		}
		return map;
	}
	
	
	/**
	 * 发送邮件
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public Map<String,Object> sendEmail(String email,HttpSession session)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		if(StringUtil.isEmpty(email)){ // 判断email是否空
			resultMap.put("success", false);
			resultMap.put("errorInfo", "邮件不能为空！");
			return resultMap;
		}
		// 验证email是否存在
		UserInformation uerInfo=userInformationService.findByEmail(email);
		if(uerInfo==null){
			resultMap.put("success", false);
			resultMap.put("errorInfo", "邮件不存在！");
			return resultMap;
		}
		String mailCode=StringUtil.genSixRandomNum();
		// 发邮件
		SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom("166768601@qq.com");//发件人
        message.setTo(email);//收件人
        message.setSubject("宅着玩资源站点-用户找回密码");//主题
        message.setText("验证码："+mailCode);//正文
        mailSender.send(message);
        // 验证码存session中
        session.setAttribute("mailCode", mailCode);
        session.setAttribute("userId", uerInfo.getId());
        resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 判断验证码,并重置密码
	 * @param yzm
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/checkYzm")
	public Map<String,Object> checkYzm(String yzm,HttpSession session)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
		String mailCode=(String) session.getAttribute("mailCode");
		Integer userId=(Integer) session.getAttribute("userId");
		if(StringUtil.isEmpty(yzm)){
			resultMap.put("success", false);
			resultMap.put("errorInfo", "验证码不能为空！");
			return resultMap;
		}
		if(!yzm.equals(mailCode)){
			resultMap.put("success", false);
			resultMap.put("errorInfo", "验证码错误！");
			return resultMap;
		}
		UserInformation user=userInformationService.getById(userId);
		user.setUserPassword(CryptographyUtil.md5("123456",Constant.SALT));
		userInformationService.save(user);
		resultMap.put("success", true);
		return resultMap;
	}
	
}
