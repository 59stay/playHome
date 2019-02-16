package com.jyb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.jyb.entity.UserInformation;
/**
 * session过滤器
 * @author jyb
 *
 */
public class SessionFilter implements Filter {
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		   System.out.println("Filter初始化中");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		   // TODO Auto-generated method stub
		  HttpServletRequest req = (HttpServletRequest)request;
		  UserInformation userSession =  (UserInformation) req.getSession().getAttribute("userInfo");
		   if (userSession != null) {
			   //先销毁在添加否则触发不了监听器中的attributeAdded
			    req.getSession().removeAttribute("userInfo");
			    //重新设值session
	            req.getSession().setAttribute("userInfo", userSession);
		   }
		    chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		   System.out.println("Filter销毁中");
	}

}
