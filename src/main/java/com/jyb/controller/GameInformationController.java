package com.jyb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.GameInformation;
import com.jyb.init.InitSystem;
import com.jyb.service.GameInformationService;
import com.jyb.util.PageUtil;

@Controller
@RequestMapping("gameInformation")
public class GameInformationController {
    
	@Autowired
	private GameInformationService gameInformationService;
	
	/**
	 * 游戏主页面
	 * @return
	 */
	 @RequestMapping("index")
	 public ModelAndView index(HttpServletRequest request){
		    request.getSession().setAttribute("tMenu", "t_0");
		    GameInformation gameIndfo  = new GameInformation();
	    	gameIndfo.setAuditStatus(1);
	    	List<GameInformation> indexGameInformationList = gameInformationService.list(gameIndfo, 1, 20,Sort.Direction.DESC, "gameCreationTime");
	    	Long total = gameInformationService.getCount(gameIndfo);
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("indexGameInformationList", indexGameInformationList);
	    	mv.addObject("pageCode",PageUtil.getPagination("/gameInformation/list",total, 1,20, ""));
	    	mv.addObject("title","宅着玩资源网站 - 宅游戏");
	    	mv.setViewName("game/gameInformation");
	    	return mv;
	 }
	
	 /**
	  * 游戏主页分页查询
	  * @param page
	  * @return
	  */
	@RequestMapping("list/{page}")
	public ModelAndView list(@RequestParam(value="gameTypeId",required=false)Integer gameTypeId,@PathVariable(value="page",required=false) Integer page,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		GameInformation gameInfo = new GameInformation();
		gameInfo.setAuditStatus(1);
		if(gameTypeId==null){
			mv.addObject("title","宅着玩资源网站 - 宅游戏 - 第"+page+"页");
		}else{
			DataDictionary gameDataDictionary = InitSystem.dataDictionaryMap.get(gameTypeId);
			gameInfo.setDataDictionary(gameDataDictionary);
			mv.addObject("title","宅着玩资源网站 - 宅游戏 - "+gameDataDictionary.getDictionaryName()+" - 第"+page+"页");
	        request.getSession().setAttribute("tMenu","t_"+gameTypeId);
		}
	
    	List<GameInformation> indexGameInformationList = gameInformationService.list(gameInfo, page, 20,Sort.Direction.DESC, "gameCreationTime");
    	Long total = gameInformationService.getCount(gameInfo);
    	
    	StringBuffer param=new StringBuffer();
		if(gameTypeId!=null){
			param.append("?gameTypeId="+gameTypeId);
		}
		mv.addObject("indexGameInformationList", indexGameInformationList);
    	mv.addObject("pageCode",PageUtil.getPagination("/gameInformation/list",total,page,20,param.toString()));
    	mv.setViewName("game/gameInformation");
		return mv;
	}
	
	@RequestMapping("listDetails/{id}")
	public ModelAndView listDetails(@PathVariable("id") Integer id){
		ModelAndView mv = new ModelAndView();
		GameInformation gameInformation = gameInformationService.getId(id);
		mv.addObject("gameInformation",gameInformation);
		mv.addObject("title", "宅着玩资源网站 - 宅游戏 - "+gameInformation.getGameTitle());
	    mv.setViewName("game/gameDetails");
		return mv;
	}
	
	
}
