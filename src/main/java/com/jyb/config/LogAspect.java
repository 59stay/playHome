package com.jyb.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jyb.entity.Log;
import com.jyb.entity.UserInformation;
import com.jyb.service.LogService;
import com.jyb.specialEntity.Constant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 日志切面类
 * @author jyb
 * 参考地址:https://blog.csdn.net/w05980598/article/details/79053209
 */
@Aspect
@Component  //申明是个spring管理的bean
public class LogAspect {
	 private Logger log = LoggerFactory.getLogger(LogAspect.class);

	 @Autowired
	 private LogService logService;


	   /**
	    * 申明一个controller切点
	    * @Pointcut("execution(public * com.king.controller..*.*(..))")
	    * execution(): 表达式主体
		* 第一个public *号：表示返回类型， *号表示所有的类型。
		* 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.king.controller包、子孙包下所有类的方法。
		* 第二个*号：表示类名，*号表示所有的类。
		* *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数
	    */
	   @Pointcut("execution(public * com.jyb.controller..*.upload*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.delete*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.add*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.modify*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.save*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.check*(..))"
	   		+ "|| execution(public * com.jyb.controller..*.update*(..))")
	   private void controllerAspect(){}



	   //引用一个ThreadLocal<Long>指定泛型的对象
	   ThreadLocal<Long>  startTime = new ThreadLocal<Long>();


	   DecimalFormat myFormat = new DecimalFormat("000000.000");

	   //请求method前打印内容
	   @Before(value = "controllerAspect()")
	   public void methodBefore(JoinPoint joinPoint) {
		  log.info("===============请求内容开始===============");
	      startTime.set(System.currentTimeMillis());//记录开始时间
		  log.info("===============请求内容结束===============");
	   }


	   //在方法执行完结后打印返回内容
	   @AfterReturning(returning = "o", pointcut = "controllerAspect()")
	   public void methodAfterReturing(JoinPoint joinPoint,Object o) {
		  log.info("===============返回内容开始===============");
		  ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	      HttpServletRequest request = requestAttributes.getRequest();
	      UserInformation userInfo = (UserInformation)request.getSession().getAttribute(Constant.USERINFO);
	      if(userInfo!=null){
	    	  log.info("操作人:" +userInfo.getUserName());
	      }
	      log.info("请求地址:" + request.getRequestURL().toString());
	      log.info("请求方式:" + request.getMethod());
	      log.info("请求类方法:" + joinPoint.getSignature());
	      log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
	      log.info("Response内容:" + JSON.toJSONString(o,SerializerFeature.DisableCircularReferenceDetect));
	      Double formatTime =(System.currentTimeMillis() - startTime.get())*0.001;
	      String runningTime = myFormat.format((formatTime));
	      log.info("请求处理时间为:"+runningTime+"秒");//记录结束时间
	      log.info("===============返回内容结束===============");
	      Log logger = new Log();
	      logger.setUserInformation(userInfo);
	      logger.setRequestAddress(request.getRequestURL().toString());
	      logger.setRequestMode(request.getMethod().toString());
	      logger.setRequestMethod(joinPoint.getSignature().toString());
	      logger.setRequestParams(Arrays.toString(joinPoint.getArgs()));
	      logger.setReturnContent(JSON.toJSONString(o,SerializerFeature.DisableCircularReferenceDetect));
	      logger.setRunningTime(Double.parseDouble(runningTime));
	      logger.setType(1);//正常
	      logger.setLogCreationTime(new Date());
	      logService.save(logger);
	   }

	    //异常通知
	     @AfterThrowing(value="",throwing="e",pointcut = "controllerAspect()")
	    public void afterReturningMethod(JoinPoint joinPoint, Exception e){
	    	 log.info("===============异常通知开始===============");
	    	 ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		      HttpServletRequest request = requestAttributes.getRequest();
		      UserInformation userInfo = (UserInformation)request.getSession().getAttribute(Constant.USERINFO);
		      if(userInfo!=null){
		    	  log.info("操作人:" +userInfo.getUserName());
		      }
		      log.info("请求地址:" + request.getRequestURL().toString());
		      log.info("请求方式:" + request.getMethod());
		      log.info("请求类方法:" + joinPoint.getSignature());
		      log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
		      log.error("异常内容:" +  JSON.toJSONString(e,SerializerFeature.DisableCircularReferenceDetect));
		      Double formatTime =(System.currentTimeMillis() - startTime.get())*0.001;
		      String runningTime = myFormat.format((formatTime));
		      log.info("请求处理时间为:"+runningTime+"秒");//记录结束时间
		      log.info("===============异常通知结束===============");
		      Log logger = new Log();
		      logger.setUserInformation(userInfo);
		      logger.setRequestAddress(request.getRequestURL().toString());
		      logger.setRequestMode(request.getMethod());
		      logger.setRequestMethod(joinPoint.getSignature().toString());
		      logger.setRequestParams(Arrays.toString(joinPoint.getArgs()));
		      logger.setException(JSON.toJSONString(e,SerializerFeature.DisableCircularReferenceDetect));
		      logger.setRunningTime(Double.parseDouble(runningTime));
		      logger.setType(2);//异常
		      logger.setLogCreationTime(new Date());
		      logService.save(logger);

	    }

}
