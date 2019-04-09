package com.jyb.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.UserReviews;
import com.jyb.service.UserReviewsService;
import com.jyb.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/userReviews")
public class AdminUserReviewsController {

	@Autowired
	private UserReviewsService userReviewsService;

	/**
     * 分页查询所有评论信息
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有评论信息"})
	public   Map<String,Object> listPage(UserReviews  userReviews ,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<UserReviews> urList =   userReviewsService.listPage(userReviews,page,limit,Sort.Direction.DESC,"reviewsTime");
		Long count = userReviewsService.getCount(userReviews);
		resultMap.put("code",0);
		resultMap.put("count",count);
		Object obj = JSONObject.toJSON(urList);
		resultMap.put("data",obj);
		return resultMap;
	}


	/**
	 * 删除评论信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteMultiple")
	@RequiresPermissions(value={"后台-删除评论信息"})
	public Map<String,Object> deleteMultiple(String ids)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(StringUtil.isNotEmpty(ids)){
			String []idsStr=ids.split(",");
			for(int i=0;i<idsStr.length;i++){
				userReviewsService.delete(Integer.parseInt(idsStr[i]));
			}
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}

}
