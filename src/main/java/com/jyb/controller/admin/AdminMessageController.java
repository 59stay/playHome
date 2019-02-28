package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jyb.entity.UserMessage;
import com.jyb.service.UserInformationService;
import com.jyb.service.UserMessageService;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("admin/userMessage")
public class AdminMessageController {
	@Autowired
	private UserMessageService userMessageService;
	
	@Autowired
	private UserInformationService userInformationService ;
    
	/**
     * 分页查询所有留言信息
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有留言信息"})
	public   Map<String,Object> listPage(UserMessage  userMessage ,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<UserMessage> umList =   userMessageService.listPage(userMessage,page,limit,Sort.Direction.DESC,"id");
		Long count = userMessageService.getCount(userMessage);
		resultMap.put("code",0);
		resultMap.put("count",count);
		Object obj = JSONObject.toJSON(umList);
		resultMap.put("data",obj);
		return resultMap;
	}
	
	/**
	 * 删除留言信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteMultiple")
	@RequiresPermissions(value={"后台-删除留言信息"})
	public Map<String,Object> deleteMultiple(String ids)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(StringUtil.isNotEmpty(ids)){
			String []idsStr=ids.split(",");
			for(int i=0;i<idsStr.length;i++){
				userMessageService.delete(Integer.parseInt(idsStr[i])); // 删除留言信息
			}
			map.put("success", true);
		}else{
			map.put("success", false);	
		}
		return map;
	}

}
