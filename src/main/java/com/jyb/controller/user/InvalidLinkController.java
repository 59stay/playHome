package com.jyb.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.GameInformation;
import com.jyb.entity.InvalidLink;
import com.jyb.entity.UserInformation;
import com.jyb.service.GameInformationService;
import com.jyb.service.InvalidLinkService;

@Controller
@RequestMapping("user/invalidLink")
public class InvalidLinkController {

	@Autowired
	private InvalidLinkService invalidLinkService;
	
	@Autowired
	private GameInformationService gameInformationService;
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
		UserInformation userInformation=(UserInformation)session.getAttribute("userInfo");
		s_invalidLink.setUserId(userInformation.getId());
		List<InvalidLink> invalidLinkList=invalidLinkService.listPage(s_invalidLink, page,limit,Sort.Direction.DESC,"creationTime");
		Long count=invalidLinkService.getCount(s_invalidLink);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", invalidLinkList);
		return resultMap;
	}
	
	 /**
     * 修改失效分享链接
     * @param article
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping("/modifyShareLink")
    public Map<String,Object> modifyShareLink(InvalidLink invalidLink,HttpSession session,HttpServletRequest request)throws Exception{
    	Map<String, Object> resultMap = new HashMap<>();
    	InvalidLink link = invalidLinkService.getId(invalidLink.getId());
    
    	if(link.getLargeCategory().equals("A")){//游戏
    		GameInformation gm = gameInformationService.getId(link.getResourceId());
    		gm.setGameDownloadAddress(invalidLink.getDownloadAddress());
    		gm.setLinkPwd(invalidLink.getLinkPwd());
    		gm.setAuditStatus(2);
    		gm.setIsUseful(1);
    		gameInformationService.save(gm);
    	}else if(link.getLargeCategory().equals("B")){ //电影
    		
    	}else{//软件
    		
    	}
    	invalidLinkService.delete(link.getId());
    	
    	resultMap.put("success", true);
    	return resultMap;
    	/*if(CheckShareLinkEnableUtil.check(invalidLink.getDownload1())){
    		Article oldArticle=articleService.get(article.getId());
    		oldArticle.setDownload1(article.getDownload1());
    		oldArticle.setPassword1(article.getPassword1());
    		oldArticle.setUseful(true);
    		articleService.save(oldArticle);
    		resultMap.put("success", true);
    		
    		User user=(User)session.getAttribute("currentUser");
        	Article s_article=new Article();
        	s_article.setUseful(false);
        	s_article.setUser(user);
        	session.setAttribute("unUsefulArticleCount", articleService.getCount(s_article));
        	redisUtil.del("article_"+article.getId());
    	}else{
    		resultMap.put("success", false);
    		resultMap.put("errorInfo", "百度云分享链接已经失效 ，请重新发布");
    	}*/
    }
	
}
