package com.jyb.controller.admin;

import com.jyb.entity.DataDictionary;
import com.jyb.init.InitSystem;
import com.jyb.service.DataDictionaryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 后台-类别Controller
 * @author jyb
 *
 */
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
	@RequiresPermissions(value={"后台-分页查询所有的类别信息"})
	public   Map<String,Object> listPage(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit,DataDictionary dataDictionary){
		Map<String,Object>   resultMap = new HashMap<String,Object>();
		List<DataDictionary> ddList =   dataDictionaryService.listPage(dataDictionary, page, limit,Sort.Direction.DESC,"dictionarySort");
		Long count = dataDictionaryService.getCount(dataDictionary);
		resultMap.put("code",0);
		resultMap.put("count",count);
		resultMap.put("data",ddList);
		return resultMap;
	}


	/**
	 * 根据id查找类别信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDataDictionary")
	@RequiresPermissions(value={"后台-根据id查找类别信息"})
	public Map<String,Object>  getDataDictionary(String id){
		Map<String,Object>   map = new HashMap<String,Object>();
		DataDictionary dataDictionary =  dataDictionaryService.getId(Integer.parseInt(id));
		map.put("dataDictionary",dataDictionary);
		map.put("success",true);
    	return map;
	}
	/**
	 * 根据大类别获取类别名称
	 * @param dictionaryType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDataDictionaryList")
	public Map<String,Object>  getDataDictionaryList(String dictionaryType){
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
	@RequiresPermissions(value={"后台-保存或修改类别信息"})
	public  Map<String,Object> saveDataDictionary(DataDictionary dataDictionary,HttpServletRequest request){
		Map<String,Object>   map = new HashMap<String,Object>();
		if(dataDictionary.getId()!=null){
			DataDictionary dd = dataDictionaryService.getId(dataDictionary.getId());
			dd.setDictionaryName(dataDictionary.getDictionaryName());
			dd.setDictionaryType(dataDictionary.getDictionaryType());
			dd.setDictionarySort(dataDictionary.getDictionarySort());
			dataDictionaryService.save(dd);
			InitSystem.loadData(request.getServletContext());
			map.put("success",true);
		}else if(dataDictionary.getId()==null){
			dataDictionaryService.save(dataDictionary);
			InitSystem.loadData(request.getServletContext());
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
	@RequiresPermissions(value={"后台-删除类别信息"})
	public Map<String,Object> delete(Integer id,HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<>();
		if(id!=null){
			dataDictionaryService.delete(id);
			InitSystem.loadData(request.getServletContext());
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		return map;
	}

}
