package com.jyb.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyb.entity.DataDictionary;
import com.jyb.service.DataDictionaryService;

@Controller
@RequestMapping("admin/dataDictionary")
public class AdminDataDictionaryController {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	
	/**
     * 分页查询所有的类别信息
     * @param page
     * @param limit
     * @return
     */
	@ResponseBody
	@RequestMapping(value="listPage")
	private   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<DataDictionary> dataDictionary =   dataDictionaryService.listPage(null, page, limit,Sort.Direction.DESC,"dictionarySort");
		Long count = dataDictionaryService.getCount(null);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",dataDictionary);
		return resultMap;
	}
	
	
	/**
	 * 根据id查找类别信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDataDictionary")
	private Map<String,Object>  getDataDictionary(String id){
		Map<String,Object>   map = new HashMap<String,Object>();
		DataDictionary dataDictionary =  dataDictionaryService.getId(Integer.parseInt(id));
		map.put("dataDictionary",dataDictionary);
		map.put("success",true);
    	return map;
	}
	/**
	 * 根据大类别获取类别名称
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDataDictionaryList")
	private Map<String,Object>  getDataDictionaryList(String dictionaryType){
		Map<String,Object>   map = new HashMap<String,Object>();
		List<DataDictionary> dataDictionary =  dataDictionaryService.findByDictionaryType(dictionaryType);
		if(dataDictionary!=null){
			map.put("dataList",dataDictionary);
			map.put("success",true);
		}else{
			map.put("dataList",dataDictionary);
			map.put("success",false);
		}
		return map;
	}
	/**
	 * 保存或修改类别信息
	 * @param dataDictionary
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveDataDictionary")
	private  Map<String,Object> saveDataDictionary(DataDictionary dataDictionary){
		Map<String,Object>   map = new HashMap<String,Object>();
		if(dataDictionary.getId()!=null){
			DataDictionary dd = dataDictionaryService.getId(dataDictionary.getId());
			dd.setDictionaryName(dataDictionary.getDictionaryName());
			dd.setDictionaryType(dataDictionary.getDictionaryType());
			dd.setDictionarySort(dataDictionary.getDictionarySort());
			dataDictionaryService.save(dd);
			map.put("success",true);
		}else if(dataDictionary.getId()==null){
			dataDictionaryService.save(dataDictionary);
			map.put("success",true);
		}else{
			map.put("success",false);
		}
		return map;
	}
	
	/**
	 * 删除类别信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(id!=null){
			dataDictionaryService.delete(id);
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}
	
}
