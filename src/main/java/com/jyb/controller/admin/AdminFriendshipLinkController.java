package com.jyb.controller.admin;

import com.jyb.entity.FriendshipLink;
import com.jyb.init.InitSystem;
import com.jyb.service.FriendshipLinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/friendshipLink")
public class AdminFriendshipLinkController {
  @Autowired
  private FriendshipLinkService friendshipLinkService;
	/**
	 *@描述    查询所有的友情链接信息
	 *@参数  []
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
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
	 *@描述  保存友情链接信息
	 *@参数  [friendshipLink, request]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
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
	 *@描述  删除友情链接信息
	 *@参数  [id, request]
	 *@返回值  java.util.Map<java.lang.String,java.lang.Object>
	 *@创建人  jyb
	 *@创建时间  2019/4/9
	 *@修改人和其它信息
     **/
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
