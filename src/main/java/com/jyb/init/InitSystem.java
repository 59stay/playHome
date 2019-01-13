package com.jyb.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.FriendshipLink;
import com.jyb.entity.GameInformation;
import com.jyb.service.DataDictionaryService;
import com.jyb.service.FriendshipLinkService;
import com.jyb.service.GameInformationService;


/**
 * tomcat初始化时加载的数据
 * @author Administrator
 *
 */
@Component("initSystem")
public class InitSystem implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public static Map<Integer,DataDictionary> dataDictionaryMap = new HashMap<Integer,DataDictionary>();
	
	
		/**
		 * 加载数据到application缓存中
		 * @param application
		 */
	public void loadData(ServletContext application){
		FriendshipLinkService friendshipLinkService=(FriendshipLinkService) applicationContext.getBean("friendshipLinkService");
		GameInformationService gameInformationService = (GameInformationService)applicationContext.getBean("gameInformationService");
		DataDictionaryService  dataDictionaryService = (DataDictionaryService)applicationContext.getBean("dataDictionaryService");
		List<GameInformation> gameBrowseFrequencyList =  gameInformationService.listPage(null, 1, 14, Sort.Direction.DESC,"gameBrowseFrequency");
		List<FriendshipLink> friendshipLinkList=friendshipLinkService.listAll(Sort.Direction.ASC, "linkId");
		DataDictionary gameDataDictionary = new DataDictionary();
		gameDataDictionary.setDictionaryType("A");
		List<DataDictionary> gameDataDictionaryList = dataDictionaryService.listAll(gameDataDictionary, Sort.Direction.ASC, "dictionarySort");
		for (DataDictionary gddl : gameDataDictionaryList) {
			dataDictionaryMap.put(gddl.getDictionaryId(),gddl);
		}
		application.setAttribute("friendshipLinkList", friendshipLinkList); // 所有友情链接
		application.setAttribute("gameBrowseFrequencyList", gameBrowseFrequencyList); // 最热门游戏链接
		application.setAttribute("gameDataDictionaryList", gameDataDictionaryList);//游戏分类列表
		
	}


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		loadData(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}

}
