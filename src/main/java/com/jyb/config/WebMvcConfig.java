package com.jyb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 参考地址：https://blog.csdn.net/qq_38762237/article/details/81283241
 * @author jyb
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
 
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//游戏内容回显图片
	registry.addResourceHandler("/gameContentImage/**").addResourceLocations("file:D:\\phImage\\gameContentImage\\");
	registry.addResourceHandler("/gameCoverImage/**").addResourceLocations("file:D:\\phImage\\gameCoverImage\\");
  }
 
}