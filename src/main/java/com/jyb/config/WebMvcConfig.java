package com.jyb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 配置图片的映射地址
 * 参考地址：https://blog.csdn.net/qq_38762237/article/details/81283241
 * @author jyb
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
 
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//游戏内容回显图片
   /* registry.addResourceHandler("/contentImage/**").addResourceLocations("file:///home/phImage/contentImage/");
	registry.addResourceHandler("/coverImage/**").addResourceLocations("file:///home/phImage/coverImage/");
	registry.addResourceHandler("/userHeadImage/**").addResourceLocations("file:///home/phImage/userHeadFilePath/");*/
    registry.addResourceHandler("/contentImage/**").addResourceLocations("file:D:\\phImage\\contentImage\\");
	registry.addResourceHandler("/coverImage/**").addResourceLocations("file:D:\\phImage\\coverImage\\");
	registry.addResourceHandler("/userHeadImage/**").addResourceLocations("file:D:\\phImage\\userHeadFilePath\\");
  }
  
 
}