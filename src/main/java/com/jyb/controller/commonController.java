package com.jyb.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyb.entity.UserInformation;
import com.jyb.specialEntity.Constant;
import com.jyb.util.DateUtil;
/**
 * 前后台共用的方法都写在这个控制层中
 * @author jyb
 *
 */
@Controller
@RequestMapping("common")
public class commonController {
	
	@Value("${contentImageFilePath}")
	private String contentImageFilePath;  //文本内容图片路径
	

	@Value("${coverImageFilePath}")
	private String coverImageFilePath;  //封面图片路径

	 /**
     * 封面图片上传处理
     * @param file
     * @param session
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/uploadCoverImage")
	public Map<String,Object> uploadCoverImage(MultipartFile file,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(coverImageFilePath+userInformation.getEmail()+"/"+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/coverImage/"+userInformation.getEmail()+"/"+newFileName);
			map.put("data", map2);
		}
		return map;
	}
	
	
	
	/**
	 * Layui编辑器图片上传处理
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadContentImage")
	public Map<String,Object> uploadContentImage(MultipartFile file,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		UserInformation userInformation =(UserInformation) session.getAttribute(Constant.USERINFO);
		if(!file.isEmpty()){
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String newFileName=DateUtil.getCurrentDateStr()+suffixName;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(contentImageFilePath+userInformation.getEmail()+"/"+newFileName));
			map.put("code", 0);
			map.put("msg", "上传成功");
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("title", newFileName);
			map2.put("src", "/contentImage/"+userInformation.getEmail()+"/"+newFileName);
			map.put("data", map2);
		}
		return map;
	}
	
	
	
}
