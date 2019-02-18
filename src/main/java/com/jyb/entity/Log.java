package com.jyb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
@Entity
@Table(name = "log")
public class Log implements Serializable{
	    private static final long serialVersionUID = 1L;
	    
		@Id
		@GeneratedValue
	    private Integer id;                 //日志主键  
		
	    private Integer type;                 //类型  1正常  2异常
	    
	    private String requestAddress;      //请求地址  
	    
	    private String requestMode;         //请求方式
	    @Lob
		@Column(columnDefinition = "longtext")
	    private String requestParams;       //请求参数 
	    
	    @Lob
		@Column(columnDefinition = "longtext")
	    private String requestMethod;       //请求方法
	    
	    @Lob
		@Column(columnDefinition = "longtext")
	    private String returnContent;       //返回内容
	    
	    @Lob
	  	@Column(columnDefinition = "longtext")
	    private String exception;           //异常信息 
	    
	    private Double runningTime;         //运行时间 
	    
	    @Transient
	    private String startTime;    //虚字段-开始时间
	    
	    @Transient
	    private String endTime;    //虚字段-结束时间
	    
	    @JSONField(format="yyyy-MM-dd HH:mm:ss")
		private Date logCreationTime;      // 创建时间
	    
	    @ManyToOne
		@JoinColumn(name = "userId")
		private UserInformation userInformation;// 所属用户
	    

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getRequestAddress() {
			return requestAddress;
		}

		public void setRequestAddress(String requestAddress) {
			this.requestAddress = requestAddress;
		}

		public String getRequestMode() {
			return requestMode;
		}

		public void setRequestMode(String requestMode) {
			this.requestMode = requestMode;
		}

		public String getRequestParams() {
			return requestParams;
		}

		public void setRequestParams(String requestParams) {
			this.requestParams = requestParams;
		}

		public String getRequestMethod() {
			return requestMethod;
		}

		public void setRequestMethod(String requestMethod) {
			this.requestMethod = requestMethod;
		}

		public String getReturnContent() {
			return returnContent;
		}

		public void setReturnContent(String returnContent) {
			this.returnContent = returnContent;
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public UserInformation getUserInformation() {
			return userInformation;
		}

		public void setUserInformation(UserInformation userInformation) {
			this.userInformation = userInformation;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Double getRunningTime() {
			return runningTime;
		}

		public void setRunningTime(Double runningTime) {
			this.runningTime = runningTime;
		}

		 

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public Date getLogCreationTime() {
			return logCreationTime;
		}

		public void setLogCreationTime(Date logCreationTime) {
			this.logCreationTime = logCreationTime;
		}

		@Override
		public String toString() {
			return "Log [id=" + id + ", type=" + type + ", requestAddress=" + requestAddress + ", requestMode="
					+ requestMode + ", requestParams=" + requestParams + ", requestMethod=" + requestMethod
					+ ", returnContent=" + returnContent + ", exception=" + exception + ", runningTime=" + runningTime
					+ ", startTime=" + startTime + ", endTime=" + endTime + ", logCreationTime=" + logCreationTime
					+ ", userInformation=" + userInformation + "]";
		}

		
}
