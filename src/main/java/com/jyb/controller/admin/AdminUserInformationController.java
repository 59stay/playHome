package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.CryptographyUtil;
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
	
	
	 /**
     * 分页查询所有的游戏资源
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	private   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<UserInformation> userInformation =   userInformationService.listPage(null, page, limit,Sort.Direction.DESC,"userCreationTime");
		Long count = userInformationService.getCount(null);
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
	private   Map<String,Object> updateAccountStatus(UserInformation userInfo){
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
	private   Map<String,Object> addIntegral(UserInformation userInfo){
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
	@RequestMapping(value="resetPassword")
	private   Map<String,Object> resetPassword(UserInformation userInfo){
		Map<String,Object>   map = new HashMap<String,Object>();
		UserInformation userInformation =   userInformationService.getById(userInfo.getId());
		userInformation.setUserPassword(CryptographyUtil.md5(Constant.PWD,Constant.SALT));
		userInformationService.save(userInformation);
		map.put("success", true);
		return map;
	}
	
	
}
