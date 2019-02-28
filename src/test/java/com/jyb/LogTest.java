package com.jyb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {

static Logger logger = LogManager.getLogger(LogTest.class.getName());
	
	public static boolean hello(){
		logger.trace("entry");	//等同于logger.entry();但此方法在新版本好像已经废弃
		
		logger.error("Did it again!");
		
		logger.info("这是info级信息");
 
		logger.debug("这是debug级信息");
		
		logger.warn("这是warn级信息222");
		
		logger.fatal("严重错误");
		
		logger.trace("exit");
		
		return false;
	}
	
	public static void main(String[] args) {
		
		logger.trace("开始主程序");
		
		for(int i = 0; i < 1000; i++){
			logger.info("当前i:"+i);
			if(!LogTest.hello()){
				logger.error("hello11111");
			}
		}
		
		logger.trace("退出程序");
	}
	
}
