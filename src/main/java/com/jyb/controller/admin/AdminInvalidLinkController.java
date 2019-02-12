package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.InvalidLink;
import com.jyb.service.InvalidLinkService;
@Controller
@RequestMapping("admin/invalidLink")
public class AdminInvalidLinkController {
	@Autowired
	private InvalidLinkService invalidLinkService;
	
	 /**
	   * 查询所有的失效链接资源
	   * @return
	   */
	    @ResponseBody
		@RequestMapping(value="listPage")
		private   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
			Map<String,Object>   resultMap = new HashMap<String,Object>();
			List<InvalidLink> invalidLinkList=invalidLinkService.listPage(null, page,limit,Sort.Direction.DESC,"creationTime");
			Long count = invalidLinkService.getCount(null);
			resultMap.put("code",0);
			resultMap.put("count",count);
			resultMap.put("data",invalidLinkList);
			return resultMap;
		}
	    
	    
}
