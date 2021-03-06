package com.jyb.controller.user;

import com.jyb.entity.GameInformation;
import com.jyb.entity.InvalidLink;
import com.jyb.entity.Software;
import com.jyb.entity.UserInformation;
import com.jyb.init.InitSystem;
import com.jyb.lucene.GameIndex;
import com.jyb.lucene.SoftwareIndex;
import com.jyb.service.GameInformationService;
import com.jyb.service.InvalidLinkService;
import com.jyb.service.SoftwareService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.CheckUrlUtil;
import com.jyb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user/invalidLink")
public class InvalidLinkController {

	@Autowired
	private InvalidLinkService invalidLinkService;

	@Autowired
	private GameInformationService gameInformationService;

	@Autowired
	private SoftwareService softwareService;

	@Autowired
	private GameIndex gameIndex;

	@Autowired
	private  SoftwareIndex softwareIndex;

	@Autowired
	private RedisUtil<GameInformation> redisUtilGame;

	@Autowired
	private RedisUtil<Software> redisUtilSoftware;


	/**
	 * 根据条件分页查询失效链接资源信息
	 * @param s_invalidLink
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userInvalidLinkList")
	public Map<String,Object> userInvalidLink(InvalidLink s_invalidLink,HttpSession session,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
		s_invalidLink.setUserId(userInformation.getId());
		List<InvalidLink> invalidLinkList=invalidLinkService.listPage(s_invalidLink, page,limit,Sort.Direction.DESC,"creationTime");
		Long count=invalidLinkService.getCount(s_invalidLink);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", invalidLinkList);
		return resultMap;
	}

	/**
	 *@描述   修改失效分享链接
	 *@参数  [invalidLink, session, request]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
	 */
    @ResponseBody
	@RequestMapping("/modifyShareLink")
    public Map<String,Object> modifyShareLink(InvalidLink invalidLink,HttpSession session,HttpServletRequest request)throws Exception{
    	Map<String, Object> resultMap = new HashMap<>();
    	InvalidLink link = invalidLinkService.getId(invalidLink.getId());
    	boolean flag =false;
    	if(link.getDownloadType()==1){
    		if(!CheckUrlUtil.checkBDY(invalidLink.getDownloadAddress())){
    			resultMap.put("success", false);
        		resultMap.put("errorInfo", "百度云分享链接已经失效 ，请重新修改链接");
    		}else{
    			flag = true;
    		}
    	}else if(link.getDownloadType()==2){
    		if(!CheckUrlUtil.checkUrl(invalidLink.getDownloadAddress(), 3000)){
    			resultMap.put("success", false);
        		resultMap.put("errorInfo", "其他分享链接已经失效 ，请重新修改链接");
    		}else{
    			flag = true;
    		}
    	}

    	if(flag){
    		if(link.getLargeCategory().equals("A")){//游戏
        		GameInformation gm = gameInformationService.getId(link.getResourceId());
        		gm.setGameDownloadAddress(invalidLink.getDownloadAddress());
        		gm.setLinkPwd(invalidLink.getLinkPwd());
        		gm.setAuditStatus(2);
        		gm.setIsUseful(1);
        		gameInformationService.save(gm);
        		gameIndex.updateIndex(gm);
        		redisUtilGame.del("r_game_"+gm.getId());
        	}
    		if(link.getLargeCategory().equals("B")){//软件
        		Software  software =  softwareService.getId(link.getResourceId());
        		software.setDownloadAddress(invalidLink.getDownloadAddress());
        		software.setLinkPwd(invalidLink.getLinkPwd());
        		software.setAuditStatus(2);
        		software.setIsUseful(1);
        		softwareService.save(software);
        		softwareIndex.updateIndex(software);
        		redisUtilSoftware.del("r_software_"+software.getId());
        	}
        	 invalidLinkService.delete(link.getId());
        	 InitSystem.loadData(request.getServletContext());
        	resultMap.put("success", true);
    	}
    	return resultMap;
    }

}
