package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.MD5Util;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("admin/userInfor")
public class AdminUserInformationController {
	
	@Autowired
	private UserInformationService userInformationService;
	
	
	@Autowired
	private JavaMailSender mailSender;
	/**
	 * 管理员登录
	 * @param userInformation
	 * @param session
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> loginUserInformation(UserInformation userInformation,String code,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil.isEmpty(userInformation.getUserName())){
			map.put("success", false);
    		map.put("errorInfo", "请输入用户名！");
		}else if(StringUtil.isEmpty(userInformation.getUserPassword())){
			map.put("success", false);
    		map.put("errorInfo", "请输入密码！");
		}else if(StringUtil.isEmpty(code)){
			map.put("success", false);
    		map.put("errorInfo", "请输入验证码！");
		}else{
			try {
				Subject subject=SecurityUtils.getSubject();
				UsernamePasswordToken token=new UsernamePasswordToken(userInformation.getUserName(), MD5Util.md5(userInformation.getUserPassword(),Constant.SALT));
				subject.login(token); // 登录验证
				String userName=(String) SecurityUtils.getSubject().getPrincipal();
				UserInformation userInfo=userInformationService.findByUserName(userName);
				if(userInfo.getUserRole()==0){
					request.getSession().setAttribute(Constant.USERINFO, userInfo);
					map.put("success", true);
				}else{
					map.put("success", false);
					map.put("errorInfo", "只有管理员账号才能登录!");
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
     * 分页查询所有用户信息
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有用户信息"})
	public   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit,UserInformation userInfo){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<UserInformation> userInformation =   userInformationService.listPage(userInfo, page, limit,Sort.Direction.DESC,"userCreationTime");
		Long count = userInformationService.getCount(userInfo);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",userInformation);
		return resultMap;
	}
	
	/**
	 * 设置账号是否禁用
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateAccountStatus")
	@RequiresPermissions(value={"后台-设置账号是否禁用"})
	public   Map<String,Object> updateAccountStatus(UserInformation userInfo){
		Map<String,Object>   map = new HashMap<String,Object>();
		UserInformation userInformation =   userInformationService.getById(userInfo.getId());
		userInformation.setAccountStatus(userInfo.getAccountStatus());
		userInformationService.save(userInformation);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 充值积分
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addIntegral")
	@RequiresPermissions(value={"后台-充值积分"})
	public   Map<String,Object> addIntegral(UserInformation userInfo){
		Map<String,Object>   map = new HashMap<String,Object>();
		UserInformation userInformation =   userInformationService.getById(userInfo.getId());
		userInformation.setUserIntegral(userInfo.getUserIntegral()+userInformation.getUserIntegral());
		userInformationService.save(userInformation);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 重置用户密码
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updatePassword")
	@RequiresPermissions(value={"后台-重置用户密码"})
	public   Map<String,Object> updatePassword(UserInformation userInfo){
		Map<String,Object>   map = new HashMap<String,Object>();
		UserInformation userInformation =   userInformationService.getById(userInfo.getId());
		userInformation.setUserPassword(MD5Util.md5(Constant.PWD,Constant.SALT));
		userInformationService.save(userInformation);
		map.put("success", true);
		return map;
	}
	
	
	/**
	 * 安全退出
	 * @return
	 */
	@RequiresPermissions(value={"后台-安全退出"})
	@GetMapping("/logout")
	public String logout(HttpSession session){
		SecurityUtils.getSubject().logout();
		return "redirect:/adminLogin.html";
	}
	
}
