package com.jyb.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.DataDictionary;
import com.jyb.entity.GameInformation;
import com.jyb.init.InitSystem;
import com.jyb.lucene.GameIndex;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.InvalidLinkService;
import com.jyb.service.UserReviewsService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.FileUtil;
import com.jyb.util.RedisUtil;

@Controller
@RequestMapping("admin/gameInformation")
public class AdminGameInformationController {

	@Autowired
	private  GameInformationService 	gameInformationService;
	
	@Autowired
	private UserReviewsService   userReviewsService;
	
	@Autowired
	private DownloadRecordService downloadRecordService;
	
	@Autowired
	private GameIndex gameIndex;
	
	@Autowired
	private InvalidLinkService  invalidLinkService;
	
	@Autowired
	private RedisUtil<GameInformation> redisUtil;
	
    /**
     * 分页查询所有的游戏资源
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有的游戏资源"})
	public   Map<String,Object> listPage(GameInformation gameInfo,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<GameInformation> gameInformation =   gameInformationService.listPage(gameInfo, page, limit,Sort.Direction.DESC,"gameCreationTime");
		Long count = gameInformationService.getCount(gameInfo);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",JSONObject.toJSON(gameInformation));
		return resultMap;
	}
	/**
	 * 根据id查找游戏资源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getGameInformation")
	public ModelAndView getGameInformation(String id){
		ModelAndView mv = new ModelAndView();
	    GameInformation gameInfo =  gameInformationService.getId(Integer.parseInt(id));
		mv.addObject("gameInformation",gameInfo);
    	mv.setViewName("admin/game/page/gameDetails");
		return mv;
	}
	
	
	/**
	 * 修改游戏信息
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions(value={"后台-修改游戏信息"})
	public Map<String,Object> update(GameInformation gameInfo,String gameTypeId,HttpSession session)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(gameInfo!=null && gameInfo.getId()!=null){
			GameInformation gameInformation=gameInformationService.getId(gameInfo.getId());
		    gameInformation.setGameName(gameInfo.getGameName());
		    gameInformation.setGameTitle(gameInfo.getGameTitle());
		    gameInformation.setGamePicture(gameInfo.getGamePicture());
		    gameInformation.setGameDescribe(gameInfo.getGameDescribe());
		    gameInformation.setIntegral(gameInfo.getIntegral());
		    gameInformation.setDownloadType(gameInfo.getDownloadType());
		    gameInformation.setGameDownloadAddress(gameInfo.getGameDownloadAddress());
		    gameInformation.setLinkPwd(gameInfo.getLinkPwd());
		    DataDictionary dd= new DataDictionary();
		    dd.setId(Integer.parseInt(gameTypeId));
		    gameInformation.setDataDictionary(dd);
		    if(gameInformation.getAuditStatus()==2||gameInformation.getAuditStatus()==3){
		      gameInformation.setAuditStatus(1);	
		    }
		    gameInformationService.save(gameInformation);
		    redisUtil.del("r_game_"+gameInfo.getId());
		    map.put("success", true);
		}else{
			map.put("success", false);
		}
        return map;
	}
	
	/**
	 * 审核通过
	 * @param id
	 * @return
	 */
	@RequestMapping("auditResourceAdopt")
	@ResponseBody
	@RequiresPermissions(value={"后台-审核通过"})
	public Map<String,Object>  auditResourceAdopt(String id,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		GameInformation gameInformation =  gameInformationService.getId(Integer.parseInt(id));
		gameInformation.setAuditStatus(2);//审核通过
		gameInformation.setIsUseful(1);//有效
		gameInformation.setReason("");
		gameInformation.setAuditDate(new Date());
		gameInformationService.save(gameInformation);
		InitSystem.loadData(request.getServletContext());
		gameIndex.updateIndex(gameInformation); 
		redisUtil.del("r_game_"+gameInformation.getId());
		invalidLinkService.deleteInvalidLink(gameInformation.getId(),gameInformation.getLargeCategory());
		map.put("success", true);
		return map;
	}
	/**
	 * 审核被驳回
	 * @param id
	 * @return
	 */
	@RequestMapping("auditResourceNotPass")
	@ResponseBody
	@RequiresPermissions(value={"后台-审核被驳回"})
	public Map<String,Object>  auditResourceNotPass(String id,String reason,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		GameInformation gameInformation =  gameInformationService.getId(Integer.parseInt(id));
		gameInformation.setAuditStatus(3);//审核未通过
		gameInformation.setAuditDate(new Date());
		gameInformation.setReason(reason);
		gameInformationService.save(gameInformation);
		map.put("success", true);
		return map;
	}
	

	/**
	 * 批量删除游戏资源
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteMultiple")
	@RequiresPermissions(value={"后台-批量删除游戏资源"})
	public Map<String,Object> deleteMultiple(String ids,HttpServletRequest request)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			gameInformationService.delete(Integer.parseInt(idsStr[i])); // 删除游戏资源信息
			userReviewsService.deleteUserReviews(Integer.parseInt(idsStr[i]),"A");//删除资源相关的评论
			downloadRecordService.deleteDownloadRecord(Integer.parseInt(idsStr[i]),"A");//删除下载的资源信息
			gameIndex.deleteIndex(String.valueOf(idsStr[i])); // 删除索引
			redisUtil.del("r_game_"+idsStr[i]);
		}
		InitSystem.loadData(request.getServletContext());
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
	/**
	 * 生成游戏资源索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexes")
	@RequiresPermissions(value={"后台-生成资源索引"})
	public Map<String,Object>  indexes (){
	    FileUtil.deleteDir(new File(Constant.LUCENE1));
		List<GameInformation> gameList=gameInformationService.listAll(null,Sort.Direction.DESC, "gameBrowseFrequency");
		for(GameInformation game:gameList){
		    gameIndex.addIndex(game);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
	
}
