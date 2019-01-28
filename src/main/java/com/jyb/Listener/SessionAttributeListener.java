package com.jyb.Listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.jyb.entity.UserInformation;
import com.jyb.specialEntity.AgentThreadLocal;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {
	 
	@Override
	//创建session时触发
	public void attributeAdded(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		  if ("userInfo".equals(event.getName())) {
	            AgentThreadLocal.set((UserInformation) event.getValue());
	        }
	}

	@Override
	//销毁session时触发
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		if ("userInfo".equals(event.getName())) {
            AgentThreadLocal.remove();
        }
	}

	@Override
	//替换session时触发
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		 if ("userInfo".equals(event.getName())) {
	            AgentThreadLocal.set((UserInformation) event.getValue());
	        }
	}

}
