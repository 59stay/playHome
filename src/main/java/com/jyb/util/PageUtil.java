package com.jyb.util;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @param param 请求参数
	 * @return
	 */
	public static String getPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "未查询到数据";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<div class='layui-box layui-laypage layui-laypage-default' id='layui-laypage-1' >");
			pageCode.append("<a href='"+targetUrl+"/1"+param+"'>首页</a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"/"+(currentPage-1)+param+"' class='layui-laypage-prev'>上一页</a>");
			}else{
				pageCode.append("<a href='javascript:;' class='layui-laypage-prev layui-disabled'>上一页</a>");
			}
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<span class='layui-laypage-curr'><em class='layui-laypage-em'></em><em>"+i+"</em></span>");	
				}else{
					pageCode.append("<a href='"+targetUrl+"/"+i+param+"'>"+i+"</a>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<a href='"+targetUrl+"/"+(currentPage+1)+param+"' class='layui-laypage-next'>下一页</a>");
			}else{
				pageCode.append("<a href='javascript:;' class='layui-laypage-next layui-disabled'>下一页</a>");
			}
			pageCode.append("<a href='"+targetUrl+"/"+totalPage+param+"'>尾页</a>");
			pageCode.append("</div>");
			return pageCode.toString();
		}
	}
	
	/**
	 * 生成上，下一页
	 * @param page
	 * @param totalNum
	 * @param q
	 * @param pageSize
	 * @return
	 */
	public static String getUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<div class='layui-box layui-laypage layui-laypage-default'>");
			if(page>1){
				pageCode.append("<a href='/user/gameInformation/search?page="+(page-1)+"&q="+q+"' class='layui-laypage-prev'>上一页</a>");
			}else{
				pageCode.append("<a href='#' class='layui-laypage-prev layui-disabled'>上一页</a>");
			}
			if(page<totalPage){
				pageCode.append("<a href='/user/gameInformation/search?page="+(page+1)+"&q="+q+"' class='layui-laypage-next'>下一页</a>");				
			}else{
				pageCode.append("<a href='#' class='layui-laypage-next layui-disabled'>下一页</a>");
			}
			pageCode.append("</div>");
		}
		return pageCode.toString();
	}
	
	
}
