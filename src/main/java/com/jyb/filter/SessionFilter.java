package com.jyb.filter;

import com.jyb.config.LogAspect;
import com.jyb.entity.UserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * session过滤器
 * @author jyb
 *
 */
public class SessionFilter implements Filter {
	 private Logger log = LoggerFactory.getLogger(LogAspect.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.info("Filter初始化中");
		//Pattern.compile(".*[(\\.js)||(\\.css)||(\\.png)||(\\.jpg)]");
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
		log.info("Filter销毁中");
	}

}
