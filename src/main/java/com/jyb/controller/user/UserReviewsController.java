package com.jyb.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jyb.entity.GameInformation;
import com.jyb.entity.InvalidLink;
import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.service.GameInformationService;
import com.jyb.service.UserReviewsService;
import com.jyb.specialEntity.Constant;
import com.jyb.util.SensitiveWordsUtil;
import com.jyb.util.StringUtil;

@Controller
@RequestMapping("user/userReviews")
public class UserReviewsController {

	@Autowired
	private UserReviewsService userReviewsService;
	
	
	/**
	 * 分页查询某个资源的评论信息
	 * @param s_comment
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list")
	public List<UserReviews> list(UserReviews s_userReviews,@RequestParam(value="page",required=false)Integer page){
		List<UserReviews> userReviewsList = (List<UserReviews>) JSONObject.toJSON(userReviewsService.listPage(s_userReviews, page, 6, Direction.DESC, "reviewsTime"));
		return userReviewsList;
	}
	
	@ResponseBody
    @PostMapping("/addUserReviews")
	public Map<String,Object> addUserReviews(UserReviews userReviews,HttpSession session){
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		userReviews.setReviewsTime(new Date());
		userReviews.setUserInformation(userInformation);
		Set<String> set = SensitiveWordsUtil.getBadWord(userReviews.getReviewsContent(),2);
			String mgc ="";
			for (String strSet : set) {
				mgc+= strSet+",";
			}
		    if(StringUtil.isNotEmpty(mgc)){
			mgc = mgc.substring(0,mgc.length()-1);
			String[] mgcArray = mgc.split(",");
			String  strMgc = "";
		    for (int i = 0; i < mgcArray.length; i++) {
		    	if(i==0){
		    		strMgc = SensitiveWordsUtil.filterSensitiveWords(userReviews.getReviewsContent(),mgcArray[i]);
		    	}else{
		    		strMgc = SensitiveWordsUtil.filterSensitiveWords(strMgc,mgcArray[i]);	
		    	}
			}
	    	userReviews.setReviewsContent(strMgc);
	     }
		userReviewsService.save(userReviews);
		map.put("success", true);
		return map;
	}
	
	
	/**
	 * 根据条件分页查询用户评论信息
	 * @param s_invalidLink
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userReviewsList")
	public Map<String,Object> userReviews(UserReviews s_userReviews,HttpSession session,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		UserInformation userInformation=(UserInformation)session.getAttribute(Constant.USERINFO);
		s_userReviews.setPublisherId(userInformation.getId());
		List<UserReviews> userReviewsList=userReviewsService.listPage(s_userReviews, page, limit, Direction.DESC, "reviewsTime");
		Long count=userReviewsService.getCount(s_userReviews);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", JSONObject.toJSON(userReviewsList));
		return resultMap;
	}
	
	
	/**
	 * 根据id删除评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		userReviewsService.delete(id);
		resultMap.put("success", true);
		return resultMap;
	}

	
}
