package com.jyb.init;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.FriendshipLink;
import com.jyb.entity.GameInformation;
import com.jyb.entity.Software;
import com.jyb.service.DataDictionaryService;
import com.jyb.service.FriendshipLinkService;
import com.jyb.service.GameInformationService;
import com.jyb.service.SoftwareService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	 *@描述  加载数据到application缓存中
	 *@参数  [application]
	 *@返回值  void
	 *@创建人  jyb
	 *@创建时间  2019/3/29
	 *@修改人和其它信息
	 */
	public static void loadData(ServletContext application){
		FriendshipLinkService friendshipLinkService=(FriendshipLinkService) applicationContext.getBean("friendshipLinkService");
		GameInformationService gameInformationService = (GameInformationService)applicationContext.getBean("gameInformationService");
		SoftwareService softwareService = (SoftwareService)applicationContext.getBean("softwareService");
		DataDictionaryService  dataDictionaryService = (DataDictionaryService)applicationContext.getBean("dataDictionaryService");
		GameInformation gameInfo = new GameInformation();
		gameInfo.setAuditStatus(2);//显示审核通过的
		gameInfo.setIsUseful(1);//显示未失效的资源
		List<GameInformation> gameIndexList = gameInformationService.listPage(gameInfo, 1, 14, Sort.Direction.DESC, "gameCreationTime");
		List<GameInformation> gameBrowseFrequencyList =  gameInformationService.listPage(gameInfo, 1, 14, Sort.Direction.DESC,"gameBrowseFrequency");

		Software software = new Software(2,1);
		List<Software> softwareIndexList = softwareService.listPage(software, 1, 14, Sort.Direction.DESC, "creationTime");
		List<Software> softwareBrowseFrequencyList =  softwareService.listPage(software, 1, 14, Sort.Direction.DESC,"browseFrequency");

		List<FriendshipLink> friendshipLinkList=friendshipLinkService.listAll(Sort.Direction.ASC, "id");
		DataDictionary  dataDictionary = new DataDictionary();
		dataDictionary.setDictionaryType("A");
		List<DataDictionary> gameDataDictionaryList = dataDictionaryService.listAll(dataDictionary, Sort.Direction.ASC, "dictionarySort");
		for (DataDictionary gddl : gameDataDictionaryList) {
			dataDictionaryMap.put(gddl.getId(),gddl);
		}
		dataDictionary.setDictionaryType("B");
		List<DataDictionary> softwareDataDictionaryList = dataDictionaryService.listAll(dataDictionary, Sort.Direction.ASC, "dictionarySort");
		for (DataDictionary sddl : softwareDataDictionaryList) {
			dataDictionaryMap.put(sddl.getId(),sddl);
		}
		application.setAttribute("gameIndexList", gameIndexList); // 主页游戏信息
		application.setAttribute("gameBrowseFrequencyList", gameBrowseFrequencyList); // 最热门游戏链接
		application.setAttribute("gameDataDictionaryList", gameDataDictionaryList);//游戏分类列表

		application.setAttribute("softwareIndexList", softwareIndexList); // 主页软件信息
		application.setAttribute("softwareBrowseFrequencyList", softwareBrowseFrequencyList); // 最热门软件链接
		application.setAttribute("softwareDataDictionaryList", softwareDataDictionaryList);//软件分类列表

		application.setAttribute("friendshipLinkList", friendshipLinkList); // 所有友情链接

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
