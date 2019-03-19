package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.FriendshipLink;
import com.jyb.init.InitSystem;
import com.jyb.service.FriendshipLinkService;

@Controller
@RequestMapping("admin/friendshipLink")
public class AdminFriendshipLinkController {
  @Autowired
  private FriendshipLinkService friendshipLinkService;
  /**
   * 查询所有的友情链接信息
   * @return
   */
    @ResponseBody
	@RequestMapping(value="listAll")
	@RequiresPermissions(value={"后台-查询所有的友情链接信息"})
    public   Map<String,Object> listAll(){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<FriendshipLink> friendshipLink =   friendshipLinkService.listAll(Sort.Direction.DESC,"id");
		resultMap.put("code",0);
		resultMap.put("data",friendshipLink);
		return resultMap;
	}
    
    /**
	 * 保存友情链接信息
	 * @param dataDictionary
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveFriendshipLink")
	@RequiresPermissions(value={"后台-保存友情链接信息"})
	public  Map<String,Object> saveFriendshipLink(FriendshipLink friendshipLink,HttpServletRequest request){
		Map<String,Object>   map = new HashMap<String,Object>();
		if(friendshipLink!=null){
			friendshipLinkService.save(friendshipLink);
			InitSystem.loadData(request.getServletContext());
			map.put("success",true);
		}else{
			map.put("success",false);
		}
		return map;
	}
	
	
	/**
	 * 删除友情链接信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value={"后台-删除友情链接信息"})
	public Map<String,Object> delete(Integer id,HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(id!=null){
			friendshipLinkService.delete(id);
			InitSystem.loadData(request.getServletContext());
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
}
