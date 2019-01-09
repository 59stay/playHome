/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserMessageController
 * Author:   jyb
 * Date:     2019/1/6 0:20
 * Description: 用户留言板控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jyb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.GameInformation;
import com.jyb.entity.UserMessage;
import com.jyb.init.InitSystem;
import com.jyb.service.UserMessageService;
import com.jyb.util.PageUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户留言板控制层〉
 *
 * @author jyb
 * @create 2019/1/6
 * @since 1.0.0
 */
@Controller
@RequestMapping("userMessage")
public class UserMessageController {

	@Autowired
	private UserMessageService userMessageService;
	
	/***
	 * 留言板分页查询
	 * @return
	 */
    @RequestMapping("index")
    public ModelAndView userMessageShow(){
             ModelAndView mv  = new ModelAndView();
             List<UserMessage> userMessageList =  userMessageService.listPage(1,10,Sort.Direction.DESC, "messageCreationTime");
             Long total = userMessageService.getCount();
         	 mv.addObject("userMessageList", userMessageList);
         	 mv.addObject("total", total);
         	 mv.addObject("pageCode",PageUtil.getPagination("/userMessage/list",total, 1,10,""));
             mv.setViewName("messageBoard/userMessage");
             return mv;
    }
    
    /**
	  * 点击留言板分页按钮查询
	  * @param page
	  * @return
	  */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable(value="page",required=false) Integer page,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
     	List<UserMessage> userMessageList = userMessageService.listPage(page,10,Sort.Direction.DESC, "messageCreationTime");
      	Long total = userMessageService.getCount();
		mv.addObject("userMessageList", userMessageList);
		mv.addObject("total", total);
     	mv.addObject("pageCode",PageUtil.getPagination("/userMessage/list",total,page,10,""));
   	    mv.setViewName("messageBoard/userMessage");
		return mv;
	}
	
    
    /**
     * 添加留言信息
     * @param userMessage
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public  Map<String,Object>   saveUserMessage(UserMessage  userMessage){
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(userMessage!=null){
    		userMessage.setKeyUserName("woshi1");
        	userMessage.setUserHead("/static/images/header.png");
        	userMessage.setMessageCreationTime(new Date());
        	userMessageService.save(userMessage);
        	map.put("success",true);
    	}else{
    		map.put("success",false);
    	}
    	return map;
    }
    
    
}