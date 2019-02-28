package com.jyb.controller.user;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.DataDictionary;
import com.jyb.entity.DownloadRecord;
import com.jyb.entity.GameInformation;
import com.jyb.entity.Software;
import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.init.InitSystem;
import com.jyb.lucene.GameIndex;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserReviewsService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.CommonMethodUtil;
import com.jyb.util.CookiesUtil;
import com.jyb.util.DateUtil;
import com.jyb.util.IpUtil;
import com.jyb.util.PageUtil;
import com.jyb.util.RedisUtil;
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
	
	@Autowired
	private GameIndex gameIndex;
	
	@Autowired
	private RedisUtil<GameInformation> redisUtil;
	
	
	
	@Value("${contentImageFilePath}")
	private String contentImageFilePath;
	

	@Value("${coverImageFilePath}")
	private String coverImageFilePath;
	

	
	
	/**
	 * 游戏主页面
	 * @return
	 */
	 @RequestMapping("index")
	 public ModelAndView index(HttpServletRequest request){
		    request.getSession().setAttribute("tMenu", "t_0");
		    GameInformation gameInfo  = new GameInformation();
	    	gameInfo.setAuditStatus(2);//显示审核通过的
	    	gameInfo.setIsUseful(1);//显示未失效的资源
	    	List<GameInformation> indexGameInformationList = gameInformationService.listPage(gameInfo, 1, 20, Sort.Direction.DESC, "gameCreationTime");
	    	Long total = gameInformationService.getCount(gameInfo);
	    	Integer signInNumber= CommonMethodUtil.getSignInNumber();
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("signInNumber", signInNumber);
	    	mv.addObject("indexGameInformationList", indexGameInformationList);
	    	mv.addObject("pageCode",PageUtil.getPagination("/user/gameInformation/list",total, 1,20, ""));
	    	mv.addObject("title","宅着玩资源网站 - 宅游戏");
	    	mv.setViewName("user/game/gameIndex");
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
		gameInfo.setAuditStatus(2);//显示审核通过的
		gameInfo.setIsUseful(1);//显示未失效的资源
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
    	mv.setViewName("user/game/gameIndex");
		return mv;
	}
	
	/**
	 * 查看游戏详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping("listDetails/{id}")
	public ModelAndView listDetails(@PathVariable("id") Integer id){
		ModelAndView mv = new ModelAndView();
		GameInformation r_game = null;
		String key="r_game_"+id;
		if(redisUtil.hasKey(key)){
			r_game=(GameInformation) redisUtil.get(key);
		}else{
			r_game=gameInformationService.getId(id);
			redisUtil.set(key,r_game,60*60);
		}
		Integer signInNumber= CommonMethodUtil.getSignInNumber();
		UserReviews ur=new UserReviews();
		ur.setLargeCategory(r_game.getLargeCategory());
		ur.setResourceId(r_game.getId());
		ur.setResourceName(r_game.getGameName());
		mv.addObject("signInNumber",signInNumber);//今日签到总人数
    	mv.addObject("userReviewsCount", userReviewsService.getCount(ur));
		mv.addObject("gameInformation",r_game);
		mv.addObject("title", "宅着玩资源网站 - 宅游戏 - "+r_game.getGameTitle());
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
	public Map<String,Object> uploadCoverImage(MultipartFile file,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(coverImageFilePath+userInformation.getEmail()+"/"+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/coverImage/"+userInformation.getEmail()+"/"+newFileName);
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
	public Map<String,Object> uploadContentImage(MultipartFile file,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(contentImageFilePath+userInformation.getEmail()+"/"+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/contentImage/"+userInformation.getEmail()+"/"+newFileName);
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
	@ResponseBody
	@RequestMapping("/addGameInformation")
	public  Map<String, Object>  addGameInformation(GameInformation gameInfo,HttpSession session){
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		Map<String, Object> map = new HashMap<String, Object>();
		if(gameInfo!=null && gameInfo.getDataDictionary()!=null ){
			gameInfo.setGameBrowseFrequency(StringUtil.randomInteger());
			gameInfo.setGameDownloadFrequency(0);
			gameInfo.setGameCreationTime(new Date());
			gameInfo.setAuditStatus(1);
			gameInfo.setIsUseful(1);
			gameInfo.setUserInformation(userInformation);
			gameInfo.setDataDictionary(gameInfo.getDataDictionary());
			gameInformationService.save(gameInfo);
			gameIndex.addIndex(gameInfo);
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		  return map;
	}
	
	
	/**
	 * 用户修改游戏信息
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateGame")
	@Transactional
	public Map<String,Object> updateGame(GameInformation gameInfo,HttpServletRequest request)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(gameInfo!=null && gameInfo.getDataDictionary()!=null){
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
			    List<UserReviews> userReviewsList = userReviewsService.ReviewsList(gameInfo.getId(),"A");
			    for (UserReviews ur : userReviewsList) {
			    	if(!gameInfo.getGameName().equals(ur.getResourceName())){
			    		ur.setResourceName(gameInfo.getGameName());
				    	userReviewsService.save(ur);
			    	}
				}
			    InitSystem.loadData(request.getServletContext());
			    gameIndex.updateIndex(gameInformation);
			    redisUtil.del("r_game_"+gameInformation.getId());
			    map.put("success", true);	
		   }else{
			    map.put("success", false);
		 }
        return map;
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
	@RequestMapping(value = "/gameList")
	public Map<String,Object> gameLists(GameInformation s_gameInformation,HttpSession session,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
		s_gameInformation.setUserInformation(userInformation);
		List<GameInformation> gameList=gameInformationService.listPage(s_gameInformation, page, limit,Sort.Direction.DESC,"gameCreationTime");
		Long count=gameInformationService.getCount(s_gameInformation);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", JSONObject.toJSON(gameList));
		return resultMap;
	}

	/**
	 * 根据id删除游戏资源
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(Integer id,HttpServletRequest request)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		gameInformationService.delete(id);//删除资源信息
		userReviewsService.deleteUserReviews(id,"A");//删除资源信息评论
		DownloadRecord downloadRecord = downloadRecordService.getDownloadedRecord(id,"A");
		if(downloadRecord!=null){
			downloadRecord.setIsNotExist(2);//下载记录状态改成已删除
			downloadRecordService.save(downloadRecord);
		}
		InitSystem.loadData(request.getServletContext());
		gameIndex.deleteIndex(String.valueOf(id));
		redisUtil.del("r_game_"+id);
		resultMap.put("success", true);
		return resultMap;
	}

	
	/**
	 * 关键字分词搜索
	 * @param q
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public ModelAndView search(String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		request.getSession().setAttribute("tMenu", "t_0");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		List<GameInformation> hotGameList=gameIndex.search(q);
		Integer toIndex=hotGameList.size()>=Integer.parseInt(page)*10?Integer.parseInt(page)*10:hotGameList.size();
		mav.addObject("hotGameList",hotGameList.subList((Integer.parseInt(page)-1)*10, toIndex));
		mav.addObject("pageCode", PageUtil.getUpAndDownPageCode(Integer.parseInt(page), hotGameList.size(), q, 10));
		mav.addObject("q",q);
		mav.addObject("resultTotal",hotGameList.size());
		mav.addObject("title", q);
		mav.setViewName("user/game/gameIndexResult");
		return mav;
	}
	
	
	/**
	 * 增加查看次数加 1
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateGameBrowseFrequency")
	public void updateGameBrowseFrequency(Integer id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Cookie ck1 = CookiesUtil.getCookieByName(request, "ipAddr");
		Cookie ck2 = CookiesUtil.getCookieByName(request, "gameId");
		GameInformation gameInfo = gameInformationService.getId(id);
		if(ck1!=null && ck2!=null ){
			String ipAddr = ck1.getValue();
			String gameId = ck2.getValue();
			if(!ipAddr.equals(IpUtil.getIpAddr(request))&&!gameId.equals(id.toString())){
				gameInfo.setGameBrowseFrequency(gameInfo.getGameBrowseFrequency()+1);
				gameInformationService.save(gameInfo);
				redisUtil.del("r_game_"+gameInfo.getId());
				InitSystem.loadData(request.getServletContext());
		    }
		}else{
			gameInfo.setGameBrowseFrequency(gameInfo.getGameBrowseFrequency()+1);
			gameInformationService.save(gameInfo);
			redisUtil.del("r_game_"+gameInfo.getId());
			InitSystem.loadData(request.getServletContext());
			CookiesUtil.setCookie(response, "ipAddr", IpUtil.getIpAddr(request),15);
			CookiesUtil.setCookie(response, "gameId",id.toString(),15);
		}
	}
	
	
}
