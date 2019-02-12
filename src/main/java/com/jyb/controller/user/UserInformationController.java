package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
	public Map<String,Object> loginUserInformation(UserInformation userInformation,HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil.isEmpty(userInformation.getUserName())){
			map.put("success", false);
    		map.put("errorInfo", "请输入用户名！");
		}else if(StringUtil.isEmpty(userInformation.getUserPassword())){
			map.put("success", false);
    		map.put("errorInfo", "请输入密码！");
		}else{
			try {
				Subject subject=SecurityUtils.getSubject();
				UsernamePasswordToken token=new UsernamePasswordToken(userInformation.getUserName(), CryptographyUtil.md5(userInformation.getUserPassword(),Constant.SALT));
				subject.login(token); // 登录验证
				String userName=(String) SecurityUtils.getSubject().getPrincipal();
				UserInformation userInfo=userInformationService.findByUserName(userName);
				if(userInfo.getAccountStatus()==1){
					map.put("success", false);
					map.put("errorInfo", "该用户已经被封禁，请联系管理员！");
					subject.logout();
				}else{
					session.setAttribute("userInfo", userInfo);
					map.put("success", true);
				}
			} catch (AuthenticationException e) {
				e.printStackTrace();
				map.put("success", false);
				map.put("errorInfo", "用户名或者密码错误！");
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
			userInformation.setUserHead("/static/images/face.jpg");
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
