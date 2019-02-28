package com.jyb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "timer")
public class Timer {
	@Id
	@GeneratedValue
    private Integer id;                 //主键

    private String taskName;            //任务名称
	 
    private String taskDescription;     //任务描述 
    
    private String taskClass;           //任务类
    
    private Integer timerStatic;        //状态  1开启  2关闭
    
    private String startTime;         //开启时间
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;  //创建时间  

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public Integer getTimerStatic() {
		return timerStatic;
	}

	public void setTimerStatic(Integer timerStatic) {
		this.timerStatic = timerStatic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "Timer [id=" + id + ", taskName=" + taskName + ", taskDescription=" + taskDescription + ", taskClass="
				+ taskClass + ", timerStatic=" + timerStatic + ", startTime=" + startTime + ", createTime=" + createTime
				+ "]";
	}
 
    
    
    
}
