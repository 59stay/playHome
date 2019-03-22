package com.jyb.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.Software;
import com.jyb.init.InitSystem;
import com.jyb.lucene.SoftwareIndex;
import com.jyb.service.DownloadRecordService;
import com.jyb.service.InvalidLinkService;
import com.jyb.service.SoftwareService;
import com.jyb.service.UserReviewsService;
import com.jyb.util.FileUtil;
import com.jyb.util.RedisUtil;
@Controller
@RequestMapping("admin/software")
public class AdminSoftwareController {
	@Autowired
	private SoftwareService softwareService;
	
	@Autowired
	private UserReviewsService   userReviewsService;
	
	@Autowired
	private DownloadRecordService downloadRecordService;
	
	@Autowired
	private SoftwareIndex softwareIndex;
	
	@Autowired
	private InvalidLinkService  invalidLinkService;
	
	@Autowired
	private RedisUtil<Software> redisUtil; 
	
	
	
	/**
     * 分页查询所有的软件资源
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	@RequiresPermissions(value={"后台-分页查询所有的软件资源"})
	public   Map<String,Object> listPage(Software software,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<Software> softwareList = softwareService.listPage(software, page, limit,Sort.Direction.DESC,"creationTime");
		Long count = softwareService.getCount(software);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",JSONObject.toJSON(softwareList));
		return resultMap;
	}
	/**
	 * 根据id查找游戏资源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getSoftware")
	public ModelAndView getGameInformation(String id){
		ModelAndView mv = new ModelAndView();
		Software software =  softwareService.getId(Integer.parseInt(id));
		mv.addObject("software",software);
    	mv.setViewName("admin/software/page/softwareDetails");
		return mv;
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
		Software software =  softwareService.getId(Integer.parseInt(id));
		software.setAuditStatus(2);//审核通过
		software.setIsUseful(1);//有效
		software.setReason("");
		software.setAuditDate(new Date());
		softwareService.save(software);
		InitSystem.loadData(request.getServletContext());
		softwareIndex.updateIndex(software); 
		redisUtil.del("r_software_"+software.getId());
		invalidLinkService.deleteInvalidLink(software.getId(),software.getLargeCategory());
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
		Software software =  softwareService.getId(Integer.parseInt(id));
		software.setAuditStatus(3);//审核未通过
		software.setAuditDate(new Date());
		software.setReason(reason);
		softwareService.save(software);
		map.put("success", true);
		return map;
	}
	/**
	 * 批量删除软件资源
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteMultiple")
	@RequiresPermissions(value={"后台-批量删除软件资源"})
	public Map<String,Object> deleteMultiple(String ids,HttpServletRequest request)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			softwareService.delete(Integer.parseInt(idsStr[i])); // 删除游戏资源信息
			userReviewsService.deleteUserReviews(Integer.parseInt(idsStr[i]),"B");//删除资源相关的评论
			downloadRecordService.deleteDownloadRecord(Integer.parseInt(idsStr[i]),"B");//删除下载的资源信息
			softwareIndex.deleteIndex(String.valueOf(idsStr[i])); // 删除索引
			redisUtil.del("r_software_"+idsStr[i]);
		}
		InitSystem.loadData(request.getServletContext());
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
	/**
	 * 生成软件资源索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexes")
	@RequiresPermissions(value={"后台-生成资源索引"})
	public Map<String,Object>  indexes (){
	    FileUtil.deleteDir(new File("D://home//lucene2"));
	    //FileUtil.deleteDir(new File("/home/lucene2"));
		List<Software> softwareList=softwareService.listAll(null,Sort.Direction.DESC, "browseFrequency");
  		for(Software software:softwareList){
  			softwareIndex.addIndex(software);
  		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
}
