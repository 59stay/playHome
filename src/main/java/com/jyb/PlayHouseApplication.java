package com.jyb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.jyb.filter.SessionFilter;
@ServletComponentScan
@SpringBootApplication
public class PlayHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayHouseApplication.class, args);
	}
      @Bean
	  public FilterRegistrationBean sessionFilter() {
		  FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		  SessionFilter sessionFilter = new SessionFilter();
		  registrationBean.setFilter(sessionFilter);
		  registrationBean.setOrder(Integer.MAX_VALUE);//数字越小，优先级越高；
		  registrationBean.addUrlPatterns("/user/signIn/*");
		  return registrationBean;
	  }
}
