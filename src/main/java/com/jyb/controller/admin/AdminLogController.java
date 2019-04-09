package com.jyb.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.Log;
import com.jyb.service.LogService;
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
@RequestMapping("admin/log")
public class AdminLogController {
	@Autowired
	private LogService logService;

	/**
     * 分页查询所有的日志信息
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有的日志信息"})
	public   Map<String,Object> listPage(Log log,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<Log> logList =   logService.listPage(log, page, limit,Sort.Direction.DESC,"logCreationTime");
		Long count = logService.getCount(log);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",JSONObject.toJSON(logList));
		return resultMap;
	}
	/**
	 *@描述  保存日志信息
	 *@参数  [log]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
	 */
		@ResponseBody
		@RequestMapping(value="saveLog")
		@RequiresPermissions(value={"后台-保存日志信息"})
		public  Map<String,Object> saveFriendshipLink(Log log){
			Map<String,Object>   map = new HashMap<String,Object>();
			if(log!=null){
				logService.save(log);
				map.put("success",true);
			}else{
				map.put("success",false);
			}
			return map;
		}


}
