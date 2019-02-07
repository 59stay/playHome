package com.jyb.controller.user;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.GameInformation;
import com.jyb.entity.SignIn;
import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.init.InitSystem;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.SignInService;
import com.jyb.service.UserReviewsService;
import com.jyb.util.CommonMethodUtil;
import com.jyb.util.DateUtil;
import com.jyb.util.PageUtil;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("user/gameInformation")
public class GameInformationController {
    
	@Autowired
	private GameInformationService gameInformationService;
	
	@Autowired
	private UserReviewsService userReviewsService;
	
	@Autowired
	private DownloadRecordService downloadRecordService;
	
	
	
	@Value("${gameContentImageFilePath}")
	private String gameContentImageFilePath;
	

	@Value("${gameCoverImageFilePath}")
	private String gameCoverImageFilePath;
	
	
	/**
	 * 游戏主页面
	 * @return
	 */
	 @RequestMapping("index")
	 public ModelAndView index(HttpServletRequest request){
		    request.getSession().setAttribute("tMenu", "t_0");
		    GameInformation gameIndfo  = new GameInformation();
	    	gameIndfo.setAuditStatus(2);//显示审核通过的
	    	List<GameInformation> indexGameInformationList = gameInformationService.listPage(gameIndfo, 1, 20,Sort.Direction.DESC, "gameCreationTime");
	    	Long total = gameInformationService.getCount(gameIndfo);
	    	Integer signInNumber= CommonMethodUtil.getSignInNumber();
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("signInNumber", signInNumber);
	    	mv.addObject("indexGameInformationList", indexGameInformationList);
	    	mv.addObject("pageCode",PageUtil.getPagination("/user/gameInformation/list",total, 1,20, ""));
	    	mv.addObject("title","宅着玩资源网站 - 宅游戏");
	    	mv.setViewName("user/game/gameInformation");
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
		gameInfo.setAuditStatus(2);
		if(gameTypeId==null){
			mv.addObject("title","宅着玩资源网站 - 宅游戏 - 第"+page+"页");
		}else{
			DataDictionary gameDataDictionary = InitSystem.dataDictionaryMap.get(gameTypeId);
			gameInfo.setDataDictionary(gameDataDictionary);
			mv.addObject("title","宅着玩资源网站 - 宅游戏 - "+gameDataDictionary.getDictionaryName()+" - 第"+page+"页");
	        request.getSession().setAttribute("tMenu","t_"+gameTypeId);
		}
	
    	List<GameInformation> indexGameInformationList = gameInformationService.listPage(gameInfo, page, 20,Sort.Direction.DESC, "gameCreationTime");
    	Long total = gameInformationService.getCount(gameInfo);
    	
    	StringBuffer param=new StringBuffer();
		if(gameTypeId!=null){
			param.append("?gameTypeId="+gameTypeId);
		}
		mv.addObject("indexGameInformationList", indexGameInformationList);
    	mv.addObject("pageCode",PageUtil.getPagination("/user/gameInformation/list",total,page,20,param.toString()));
    	mv.setViewName("user/game/gameInformation");
		return mv;
	}
	
	
	@RequestMapping("listDetails/{id}")
	public ModelAndView listDetails(@PathVariable("id") Integer id){
		ModelAndView mv = new ModelAndView();
		GameInformation gameInformation = gameInformationService.getId(id);
		Integer signInNumber= CommonMethodUtil.getSignInNumber();
		UserReviews ur=new UserReviews();
		ur.setLargeCategory(gameInformation.getLargeCategory());
		ur.setResourceId(gameInformation.getId());
		ur.setResourceName(gameInformation.getGameName());
		mv.addObject("signInNumber",signInNumber);//今日签到总人数
    	mv.addObject("userReviewsCount", userReviewsService.getCount(ur));
		mv.addObject("gameInformation",gameInformation);
		mv.addObject("title", "宅着玩资源网站 - 宅游戏 - "+gameInformation.getGameTitle());
	    mv.setViewName("user/game/gameDetails");
		return mv;
	}
    /**
     * 游戏封面图片上传处理
     * @param file
     * @param session
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/uploadCoverImage")
	public Map<String,Object> uploadCoverImage(MultipartFile file)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(gameCoverImageFilePath+DateUtil.getCurrentDatePath()+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/gameCoverImage/"+DateUtil.getCurrentDatePath()+newFileName);
			map.put("data", map2);
		}
		return map;
	}
	
	/**
	 * Layui编辑器图片上传处理
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadContentImage")
	public Map<String,Object> uploadContentImage(MultipartFile file)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(gameContentImageFilePath+DateUtil.getCurrentDatePath()+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/gameContentImage/"+DateUtil.getCurrentDatePath()+newFileName);
			map.put("data", map2);
		}
		return map;
	}
	/**
	 * 用户发布游戏信息
	 * @param gameInfo
	 * @param session
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView addGameInformation(GameInformation gameInfo,HttpSession session){
		UserInformation userInformation =(UserInformation) session.getAttribute("userInfo");
		userInformation.getId();
		gameInfo.setGameBrowseFrequency(StringUtil.randomInteger());
		gameInfo.setGameDownloadFrequency(StringUtil.randomInteger());
		gameInfo.setGameCreationTime(new Date());
		gameInfo.setAuditStatus(1);
		gameInfo.setUserInformation(userInformation);
		gameInformationService.save(gameInfo);
		ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "发布游戏成功页面");
    	mav.setViewName("user/game/publishGameSuccess");
		return mav;
	}
	
	
	/**
	 * 用户修改游戏信息
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public ModelAndView update(GameInformation gameInfo)throws Exception{
	    GameInformation gameInformation=gameInformationService.getId(gameInfo.getId());
	    gameInformation.setGameName(gameInfo.getGameName());
	    gameInformation.setGameTitle(gameInfo.getGameTitle());
	    gameInformation.setGamePicture(gameInfo.getGamePicture());
	    gameInformation.setGameDescribe(gameInfo.getGameDescribe());
	    gameInformation.setDownloadType(gameInfo.getDownloadType());
	    gameInformation.setIntegral(gameInfo.getIntegral());
	    gameInformation.setGameDownloadAddress(gameInfo.getGameDownloadAddress());
	    gameInformation.setLinkPwd(gameInfo.getLinkPwd());
	    gameInformation.setDataDictionary(gameInfo.getDataDictionary());
	    if(gameInformation.getAuditStatus()==2||gameInformation.getAuditStatus()==3){
	      gameInformation.setAuditStatus(1);	
	    }
	    gameInformationService.save(gameInformation);
		ModelAndView mav=new ModelAndView();
    	mav.addObject("title", "修改游戏成功页面");
    	mav.setViewName("user/game/modifyGameSuccess");
        return mav;
	}
	
	/**
	 * 根据条件分页查询用户发布的游戏资源信息
	 * @param s_gameInformation
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userGameList")
	public Map<String,Object> userGame(GameInformation s_gameInformation,HttpSession session,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
		s_gameInformation.setUserInformation(userInformation);
		List<GameInformation> articleList=gameInformationService.listPage(s_gameInformation, page, limit,Sort.Direction.DESC,"gameCreationTime");
		Long count=gameInformationService.getCount(s_gameInformation);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", articleList);
		return resultMap;
	}

	/**
	 * 根据id删除帖子
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		gameInformationService.delete(id);//删除资源信息
		userReviewsService.deleteUserReviews(id,"A");//删除资源信息评论
		downloadRecordService.deleteDownloadRecord(id, "A");//删除下载的资源信息
		resultMap.put("success", true);
		return resultMap;
	}

	
	
	
}
