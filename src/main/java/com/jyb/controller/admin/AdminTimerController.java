package com.jyb.controller.admin;

import com.jyb.entity.Timer;
import com.jyb.service.TimerService;
import com.jyb.timer.CheckLinkRunnable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Controller
@RequestMapping("/admin/timer")
public class AdminTimerController {

	    @Autowired
	    private TimerService timerService;

	    @Autowired
	    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	    private ScheduledFuture<?> future1;


	    @Bean
	    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
	        return new ThreadPoolTaskScheduler();
	    }


	    /**
	     * 查询所有的定时任务信息
	     * @return
	     */
	    @ResponseBody
	  	@RequestMapping(value="listAll")
	  	@RequiresPermissions(value={"后台-查询所有的定时任务信息"})
        public   Map<String,Object> listAll(){
	  		Map<String,Object>   resultMap = new HashMap<String,Object>();
	  		List<Timer> timerLink = timerService.listAll();
	  		resultMap.put("code",0);
	  		resultMap.put("data",timerLink);
	  		return resultMap;
  	    }


	    /**
		 * 保存或修改定时任务
		 * @param timer
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="addTimer")
		@RequiresPermissions(value={"后台-保存定时器信息"})
		public  Map<String,Object> addTimer(Timer timer){
			Map<String,Object>   map = new HashMap<String,Object>();
			if(timer!=null){
				if(timer.getId()==null){
					timerService.save(timer);
				}else{
					Timer t =	timerService.getId(timer.getId());
					t.setTaskName(timer.getTaskName());
					t.setTaskDescription(timer.getTaskDescription());
					t.setStartTime(timer.getStartTime());
					t.setTaskClass(timer.getTaskClass());
				    t.setCreateTime(new Date());
				}
				map.put("success",true);
			}else{
				map.put("success",false);
			}
			return map;
		}

	    /**
	     * 开启定时器
	     * @param t
	     * @return
	     */
	    @ResponseBody
		@RequestMapping(value="updateStartTimer1")
	    @RequiresPermissions(value={"后台-开启定时器信息"})
	    public Map<String,Object> updateStartTimer1(Timer t) {
	    	Map<String,Object>   map = new HashMap<String,Object>();
	    	Timer timer =	timerService.getId(t.getId());
        	future1 = threadPoolTaskScheduler.schedule(new CheckLinkRunnable(),new Trigger(){
	            public Date nextExecutionTime(TriggerContext triggerContext){
	                return new CronTrigger(timer.getStartTime()).nextExecutionTime(triggerContext);
	            }
	        });
            timer.setTimerStatic(t.getTimerStatic());
        	timerService.save(timer);
        	map.put("success", true);
        	return map;
	    }

	    /**
	     * 关闭定时器
	     * @return
	     */
	    @ResponseBody
		@RequestMapping(value="updateStopTimer1")
	    @RequiresPermissions(value={"后台-关闭定时器信息"})
	    public Map<String,Object> updateStopTimer1(Timer t) {
	    	Map<String,Object>   map = new HashMap<String,Object>();
	        if (future1 != null) {
	            future1.cancel(true);
	        }
	    	Timer timer =	timerService.getId(t.getId());
        	timer.setTimerStatic(t.getTimerStatic());
        	timerService.save(timer);
        	map.put("success", true);
        	return map;
	    }



}
