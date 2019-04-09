package com.jyb.controller.user;

import com.google.gson.Gson;
import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.Constant;
import com.jyb.specialEntity.VaptchaMessage;
import com.jyb.util.DateUtil;
import com.jyb.util.IpUtil;
import com.jyb.util.MD5Util;
import com.jyb.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.*;
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

	@Value("${userHeadFilePath}")
	private String userHeadFilePath;

	/**
	 * 用户登录
	 * @param userInformation
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> loginUserInformation(UserInformation userInformation,HttpServletRequest request,HttpSession session,String vaptcha_token) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil.isEmpty(userInformation.getUserName())){
			map.put("success", false);
    		map.put("errorInfo", "请输入用户名！");
    		return map;
		}else if(StringUtil.isEmpty(userInformation.getUserPassword())){
			map.put("success", false);
    		map.put("errorInfo", "请输入密码！");
    		return map;
		}/*else if(!vaptchaCheck(vaptcha_token,request.getRemoteHost())){
				map.put("success", false);
	    		map.put("errorInfo", "人机验证失败！");
	    		return map;
		}*/else{
			try {
				Subject subject=SecurityUtils.getSubject();
				UsernamePasswordToken token=new UsernamePasswordToken(userInformation.getUserName(), MD5Util.md5(userInformation.getUserPassword(),Constant.SALT));
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
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(@Valid UserInformation userInformation,BindingResult bindingResult,HttpServletRequest request,String yzm){
		Map<String,Object> map = new HashMap<String,Object>();
		String mailCode=(String) request.getSession().getAttribute("mailCode");
		if(bindingResult.hasErrors()){
			map.put("success",false);
			map.put("errorInfo",bindingResult.getFieldError().getDefaultMessage());
		}else if(StringUtil.isEmpty(yzm)){
			map.put("success", false);
			map.put("errorInfo", "验证码不能为空！");
		}else if(!yzm.equals(mailCode)){
			map.put("success", false);
			map.put("errorInfo", "验证码错误！");
		}else{
			request.getSession().removeAttribute("mailCode");
			userInformation.setUserPassword(MD5Util.md5(userInformation.getUserPassword(),Constant.SALT));
			userInformation.setUserCreationTime(new Date());
			userInformation.setUserHead("/static/images/face.jpg");
			userInformation.setAccountStatus(0);
			userInformation.setUserIntegral(0);
			userInformation.setUserRole(1);
			userInformation.setUserIntegral(50);//送50积分
			userInformation.setHostIp(IpUtil.getIpAddr(request));
			userInformationService.save(userInformation);
			map.put("success",true);
		}
		return map;
	}

	/**
	 *  验证邮箱是否存在
	 * @param email
	 * @param i 0找回密码   1注册账号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkEmail")
	public Map<String,Object> checkEmail(String email,Integer i){
		Map<String, Object> map = new HashMap<>();
		UserInformation uerInfo=userInformationService.findByEmail(email);
		if(StringUtil.isEmpty(email)){ // 判断email是否空
			map.put("success", false);
			map.put("errorInfo", "请输入QQ邮箱！");
		}else if(uerInfo==null && i==0){
			map.put("success", false);
			map.put("errorInfo", "QQ邮箱不存在,请重新输入！");
		}else if(uerInfo!=null && i==1){
			map.put("success", false);
			map.put("errorInfo", "QQ邮箱已存在，请更换！");
		}else{
			map.put("success", true);
		}
		return map;
	}

	/**
	 *@描述  发送邮件
	 *@参数  [email, session, i]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public Map<String,Object> sendEmail(String email,HttpSession session,Integer i)throws Exception{
		Map<String, Object> map = new HashMap<>();
		session.removeAttribute("mailCode");
		String mailCode=StringUtil.genSixRandomNum();
		// 发邮件
		SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(Constant.QQEMAIL);//发件人
        message.setTo(email);//收件人
        if(i==0){
        	UserInformation uerInfo=userInformationService.findByEmail(email);
    		if(uerInfo!=null && uerInfo.getId()!=null ){
    			 session.setAttribute("userId", uerInfo.getId());
    		}
        	 message.setSubject("宅着玩资源站点-用户找回密码");//主题
        }
        if(i==1){
        	 message.setSubject("宅着玩资源站点-用户注册");//主题
        }
        message.setText("您的验证码是："+mailCode); //邮件内容
        mailSender.send(message);
        // 验证码存session中
        session.setAttribute("mailCode", mailCode);
        map.put("success", true);
		return map;
	}

	/**
	 * 上传用户头像
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadUserHead")
	public Map<String,Object> uploadUserHead(MultipartFile file,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(userHeadFilePath+userInformation.getEmail()+"/"+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/userHeadImage/"+userInformation.getEmail()+"/"+newFileName);
			map.put("data", map2);
		}
		return map;
	}

	/**
	 *@描述  修改用户信息
	 *@参数  [userInformation, session]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyUserInfo")
	public Map<String, Object> modifyUserInfo(UserInformation userInformation,HttpSession session){
		  Map<String, Object> map = new HashMap<>();
		  UserInformation userInfo  = userInformationService.getById( userInformation.getId());
		  userInfo.setSex(userInformation.getSex());
		  userInfo.setUserPhoneNumber(userInformation.getUserPhoneNumber());
		  userInfo.setUserIdcard(userInformation.getUserIdcard());
		  userInfo.setUserHead(userInformation.getUserHead());
		  userInformationService.save(userInfo);
		  map.put("success", true);
		  return  map;
	}

	/**
	 * 修改用户密码
	 * @param password
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyPassword")
	public Map<String,Object> modifyPassword(String oldPassword,String password,String confirmPassword,HttpSession session){
		  Map<String, Object> map = new HashMap<>();
		  if(StringUtil.isNotEmpty(oldPassword)&& StringUtil.isNotEmpty(password)){
			  UserInformation userInfo=(UserInformation)session.getAttribute(Constant.USERINFO);
			  String md5 = MD5Util.md5(oldPassword,Constant.SALT);
			  if(userInfo.getUserPassword().equals(md5)){
				  userInfo.setUserPassword(MD5Util.md5(confirmPassword,Constant.SALT));
				  userInformationService.save(userInfo);
				  map.put("success", true);
			  }else{
				  map.put("success", false);
				  map.put("errorInfo", "原密码错误");
			  }
		  }else if(!password.equals(confirmPassword)){
			  map.put("success", false);
			  map.put("errorInfo", "两次新密码输入的不相同");
		  }else{
			  map.put("success", false);
			  map.put("errorInfo", "请输入密码");
		  }
		  return map;
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
		user.setUserPassword(MD5Util.md5("123456",Constant.SALT));
		userInformationService.save(user);
		session.removeAttribute("mailCode");
		resultMap.put("userName",user.getUserName());
		resultMap.put("success", true);
		return resultMap;
	}


	/**
	 * 人机验证结果判断
	 * @param token
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private boolean vaptchaCheck(String token,String ip)throws Exception{
		String body="";
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://api.vaptcha.com/v2/validate");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "5c7754cbfc650fe020328b0e"));
		nvps.add(new BasicNameValuePair("secretkey", "04bc8ca8f0a247be8d0d8a3b5c190df3"));
		nvps.add(new BasicNameValuePair("scene", ""));
		nvps.add(new BasicNameValuePair("token", token));
		nvps.add(new BasicNameValuePair("ip", ip));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse r = httpClient.execute(httpPost);
		HttpEntity entity = r.getEntity();
		if(entity!=null){
			body = EntityUtils.toString(entity, "utf-8");
			//System.out.println(body);
		}
		r.close();
		httpClient.close();
		Gson gson = new Gson();
		VaptchaMessage message=gson.fromJson(body, VaptchaMessage.class);
		if(message.getSuccess()==1){
			return true;
		}else{
			return false;
		}

	}

}
