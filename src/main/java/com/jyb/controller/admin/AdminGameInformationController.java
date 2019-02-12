package com.jyb.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.GameInformation;
import com.jyb.init.InitSystem;
import com.jyb.lucene.GameIndex;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserReviewsService;
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
	private RedisUtil<GameInformation> redisUtil;
	
    /**
     * 分页查询所有的游戏资源
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	private   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<GameInformation> gameInformation =   gameInformationService.listPage(null, page, limit,Sort.Direction.DESC,"gameCreationTime");
		Long count = gameInformationService.getCount(null);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",gameInformation);
		return resultMap;
	}
	/**
	 * 根据id查找游戏资源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getGameInformation")
	private ModelAndView getGameInformation(String id){
		ModelAndView mv = new ModelAndView();
	    GameInformation gameInfo =  gameInformationService.getId(Integer.parseInt(id));
		mv.addObject("gameInformation",gameInfo);
    	mv.setViewName("admin/game/page/saveGameResource");
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
		    redisUtil.del("r_game"+gameInfo.getId());
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
	public Map<String,Object>  auditResourceAdopt(String id,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		GameInformation gameInformation =  gameInformationService.getId(Integer.parseInt(id));
		gameInformation.setAuditStatus(2);//审核通过
		gameInformation.setReason("");
		gameInformation.setAuditDate(new Date());
		gameInformationService.save(gameInformation);
		InitSystem.loadData(request.getServletContext());
		gameIndex.updateIndex(gameInformation); 
		redisUtil.del("r_game"+gameInformation.getId());
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
	@RequestMapping("/batchDelete")
	public Map<String,Object> batchDelete(String ids,HttpServletRequest request)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			gameInformationService.delete(Integer.parseInt(idsStr[i])); // 删除游戏资源信息
			userReviewsService.deleteUserReviews(Integer.parseInt(idsStr[i]),"A");//删除资源相关的评论
			downloadRecordService.deleteDownloadRecord(Integer.parseInt(idsStr[i]),"A");//删除下载的资源信息
			gameIndex.deleteIndex(String.valueOf(idsStr[i])); // 删除索引
			redisUtil.del("r_game"+idsStr[i]);
		}
		InitSystem.loadData(request.getServletContext());
		
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
}