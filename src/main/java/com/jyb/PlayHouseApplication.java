package com.jyb;

import com.jyb.filter.SessionFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
@ServletComponentScan
@SpringBootApplication
public class PlayHouseApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PlayHouseApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PlayHouseApplication.class, args);
	}
	  /**
	   * session过滤器
	   * @return
	   */
      @Bean
	  public FilterRegistrationBean sessionFilter() {
		  FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		  SessionFilter sessionFilter = new SessionFilter();
		  registrationBean.setFilter(sessionFilter);
		  registrationBean.setOrder(Integer.MAX_VALUE);//数字越小，优先级越高；
		  registrationBean.addUrlPatterns("/user/signIn/*");
	      //registrationBean.setEnabled(false);
	      return registrationBean;
	  }
}
