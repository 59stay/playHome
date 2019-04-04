package com.jyb.config;

import com.jyb.filter.IpFilter;
import com.jyb.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 配置Filter初始信息类
 * 参考地址：https://blog.csdn.net/qq_38762237/article/details/81283241
 * @author jyb
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     *@描述  获取到session对象 在非controller中进行调用
     *@参数  []
     *@返回值  org.springframework.boot.web.servlet.FilterRegistrationBean
     *@创建人  jyb
     *@创建时间  2019/3/29
     *@修改人和其它信息
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

    /**
     *@描述  过滤恶意访问网站信息的请求的ip
     *@参数  []
     *@返回值  org.springframework.boot.web.servlet.FilterRegistrationBean
     *@创建人  jyb
     *@创建时间  2019/3/29
     *@修改人和其它信息
     */
    @Bean
    public FilterRegistrationBean ipFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        IpFilter ipFilter = new IpFilter();
        registrationBean.setFilter(ipFilter);
        registrationBean.setOrder(Integer.MAX_VALUE-1);//数字越小，优先级越高；
        registrationBean.addUrlPatterns("/user/*");
        //registrationBean.setEnabled(false);
        return registrationBean;
    }

    /**
     *@描述 图片上传路径映射
     *@参数  [registry]
     *@返回值  void
     *@创建人  jyb
     *@创建时间  2019/3/29
     *@修改人和其它信息
     */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//游戏内容回显图片
    /*registry.addResourceHandler("/contentImage/**").addResourceLocations("file:///home/phImage/contentImage/");
	registry.addResourceHandler("/coverImage/**").addResourceLocations("file:///home/phImage/coverImage/");
	registry.addResourceHandler("/userHeadImage/**").addResourceLocations("file:///home/phImage/userHeadFilePath/");*/

   registry.addResourceHandler("/contentImage/**").addResourceLocations("file:D:\\phImage\\contentImage\\");
	registry.addResourceHandler("/coverImage/**").addResourceLocations("file:D:\\phImage\\coverImage\\");
	registry.addResourceHandler("/userHeadImage/**").addResourceLocations("file:D:\\phImage\\userHeadFilePath\\");
  }


}