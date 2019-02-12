package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyb.entity.FriendshipLink;
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
	private   Map<String,Object> listAll(){
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
	private  Map<String,Object> saveFriendshipLink(FriendshipLink friendshipLink){
		Map<String,Object>   map = new HashMap<String,Object>();
		if(friendshipLink!=null){
			friendshipLinkService.save(friendshipLink);
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
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(id!=null){
			friendshipLinkService.delete(id);
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
}
