package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.DataDictionary;
import com.jyb.entity.DownloadRecord;
import com.jyb.entity.GameInformation;
import com.jyb.entity.Software;
import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.init.InitSystem;
import com.jyb.lucene.SoftwareIndex;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.SoftwareService;
import com.jyb.service.UserReviewsService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.CommonMethodUtil;
import com.jyb.util.CookiesUtil;
import com.jyb.util.IpUtil;
import com.jyb.util.PageUtil;
import com.jyb.util.RedisUtil;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("user/software")
public class SoftwareController {

	@Autowired
	private SoftwareService softwareService;
	
	@Autowired
	private RedisUtil<Software> redisUtil;
	
	@Autowired
	private UserReviewsService userReviewsService;
	
	@Autowired
	private  SoftwareIndex softwareIndex;
	
	@Autowired
	private DownloadRecordService downloadRecordService;
	
	
	/**
	 * 软件主页面
	 * @return
	 */
	 @RequestMapping("index")
	 public ModelAndView index(HttpServletRequest request){
		    request.getSession().setAttribute("tMenu", "t_0");
		    Software software  = new Software(2,1);
	    	List<Software> indexSoftwareList = softwareService.listPage(software, 1, 20, Sort.Direction.DESC, "creationTime");
	    	Long total = softwareService.getCount(software);
	    	Integer signInNumber= CommonMethodUtil.getSignInNumber();
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("signInNumber", signInNumber);
	    	mv.addObject("indexSoftwareList", indexSoftwareList);
	    	mv.addObject("pageCode",PageUtil.getPagination("/user/software/list",total, 1,20, ""));
	    	mv.addObject("title","宅着玩资源网站 - 宅软件");
	    	mv.setViewName("user/software/softwareIndex");
	    	return mv;
	 }
	 
	 /**
	  * 软件主页分页查询
	  * @param page
	  * @return
	  */
	@RequestMapping("list/{page}")
	public ModelAndView list(@RequestParam(value="typeId",required=false)Integer typeId,@PathVariable(value="page",required=false) Integer page,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		Software software  = new Software(2,1);
		if(typeId==null){
			mv.addObject("title","宅着玩资源网站 - 宅软件 - 第"+page+"页");
		}else{
			DataDictionary gameDataDictionary = InitSystem.dataDictionaryMap.get(typeId);
			software.setDataDictionary(gameDataDictionary);
			mv.addObject("title","宅着玩资源网站 - 宅软件 - "+gameDataDictionary.getDictionaryName()+" - 第"+page+"页");
	        request.getSession().setAttribute("tMenu","t_"+typeId);
		}
	
    	List<Software> indexSoftwareList = softwareService.listPage(software, page, 20,Sort.Direction.DESC, "creationTime");
    	Long total = softwareService.getCount(software);
    	
    	StringBuffer param=new StringBuffer();
		if(typeId!=null){
			param.append("?typeId="+typeId);
		}
		mv.addObject("indexSoftwareList", indexSoftwareList);
    	mv.addObject("pageCode",PageUtil.getPagination("/user/software/list",total,page,20,param.toString()));
    	mv.setViewName("user/software/softwareIndex");
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
		Software r_software = null;
		String key="r_software_"+id;
		if(redisUtil.hasKey(key)){
			r_software=(Software)redisUtil.get(key);
		}else{
			r_software=softwareService.getId(id);
			redisUtil.set(key,r_software,60*60);
		}
		Integer signInNumber= CommonMethodUtil.getSignInNumber();
		UserReviews ur=new UserReviews();
		ur.setLargeCategory(r_software.getLargeCategory());
		ur.setResourceId(r_software.getId());
		ur.setResourceName(r_software.getName());
		mv.addObject("signInNumber",signInNumber);//今日签到总人数
    	mv.addObject("userReviewsCount", userReviewsService.getCount(ur));
		mv.addObject("software",r_software);
		mv.addObject("title", "宅着玩资源网站 - 宅游戏 - "+r_software.getTitle());
	    mv.setViewName("user/software/softwareDetails");
		return mv;
	}
	
	
	/**
	 * 用户发布软件信息
	 * @param software
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSoftware")
	@Transactional
	public Map<String, Object> addSoftware(Software software,HttpSession session){
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
	    Map<String, Object> map = new HashMap<String, Object>();
		if(software!=null && software.getDataDictionary()!=null){
			software.setBrowseFrequency(StringUtil.randomInteger());
			software.setDownloadFrequency(0);
			software.setCreationTime(new Date());
			software.setAuditStatus(1);
			software.setIsUseful(1);
			software.setUserInformation(userInformation);
			software.setDataDictionary(software.getDataDictionary());
			softwareService.save(software);
			map.put("success", true);
		}else{
			map.put("success", false);
		}
	    map.put("success", true);
	    return map;
	}
	 
	
	/**
	 * 修改软件信息
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Transactional
	public  Map<String, Object>  update(Software u_software,HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Software software=softwareService.getId(u_software.getId());
		software.setName(u_software.getName());
		software.setTitle(u_software.getTitle());
		software.setPicture(u_software.getPicture());
		software.setResourcesDescribe(u_software.getResourcesDescribe());
		software.setDownloadType(u_software.getDownloadType());
		software.setIntegral(u_software.getIntegral());
		software.setDownloadAddress(u_software.getDownloadAddress());
		software.setLinkPwd(u_software.getLinkPwd());
		software.setDataDictionary(u_software.getDataDictionary());
	    if(software.getAuditStatus()==2||software.getAuditStatus()==3){
	    	software.setAuditStatus(1);	
	    }
	    softwareService.save(software);
	    List<UserReviews> userReviewsList = userReviewsService.ReviewsList(u_software.getId(),"B");
	    for (UserReviews ur : userReviewsList) {
	    	if(!u_software.getName().equals(ur.getResourceName())){
	    		ur.setResourceName(u_software.getName());
		    	userReviewsService.save(ur);
	    	}
		}
	    
	    InitSystem.loadData(request.getServletContext());
	    softwareIndex.updateIndex(software);
	    redisUtil.del("r_software_"+software.getId());
	    map.put("success",true);
        return map;
	}
	
	/**
	 * 根据条件分页查询用户发布的软件资源信息
	 * @param s_gameInformation
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/softwareList")
	public Map<String,Object> softwareList(Software s_software,HttpSession session,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
		s_software.setUserInformation(userInformation);
		List<Software> softwareList=softwareService.listPage(s_software, page, limit,Sort.Direction.DESC,"creationTime");
		Long count=softwareService.getCount(s_software);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", JSONObject.toJSON(softwareList));
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
		List<Software> hotSoftwareList=softwareIndex.search(q);
		Integer toIndex=hotSoftwareList.size()>=Integer.parseInt(page)*10?Integer.parseInt(page)*10:hotSoftwareList.size();
		mav.addObject("hotSoftwareList",hotSoftwareList.subList((Integer.parseInt(page)-1)*10, toIndex));
		mav.addObject("pageCode", PageUtil.getUpAndDownPageCode("/user/software/search?page",Integer.parseInt(page), hotSoftwareList.size(), q, 10));
		mav.addObject("q",q);
		mav.addObject("resultTotal",hotSoftwareList.size());
		mav.addObject("title", q);
		mav.setViewName("user/software/softwareIndexResult");
		return mav;
	}
	
	/**
	 * 根据id删除软件资源
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(Integer id,HttpServletRequest request)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		softwareService.delete(id);//删除资源信息
		userReviewsService.deleteUserReviews(id,"B");//删除资源信息评论
		DownloadRecord downloadRecord = downloadRecordService.getDownloadedRecord(id,"B");
		if(downloadRecord!=null){
			downloadRecord.setIsNotExist(2);//下载记录状态改成已删除
			downloadRecordService.save(downloadRecord);
		}
		InitSystem.loadData(request.getServletContext());
		softwareIndex.deleteIndex(String.valueOf(id));
		redisUtil.del("r_software_"+id);
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 增加查看次数每次加1
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateSoftwareBrowseFrequency")
	public void updateSoftwareBrowseFrequency(Integer id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Cookie ck1 = CookiesUtil.getCookieByName(request, "ipAddr");
		Cookie ck2 = CookiesUtil.getCookieByName(request, "softwareId");
		Software software = softwareService.getId(id);
		if(ck1!=null && ck2!=null ){
			String ipAddr = ck1.getValue();
			String softwareId = ck2.getValue();
			if(!ipAddr.equals(IpUtil.getIpAddr(request))&&!softwareId.equals(id.toString())){
				software.setBrowseFrequency(software.getBrowseFrequency()+1);
				softwareService.save(software);
		    }
		}else{
			software.setBrowseFrequency(software.getBrowseFrequency()+1);
			softwareService.save(software);
			CookiesUtil.setCookie(response, "ipAddr", IpUtil.getIpAddr(request),15);
			CookiesUtil.setCookie(response, "softwareId",id.toString(),15);
		}
	}
}
