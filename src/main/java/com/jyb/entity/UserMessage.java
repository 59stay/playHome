package com.jyb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;

@Entity
@Table(name="user_message")
public class UserMessage {

	@Id 
	@GeneratedValue
	private Integer id;   
	
	@Column(length=500)
	private String messageInformation; // 留言信息
	
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date  messageCreationTime;//留言时间

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInformation userInformation;//留言用户 
	@Transient
	private Date startDate;//虚字段-开始时间
	@Transient
	private Date endDate;//虚字段-结束时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessageInformation() {
		return messageInformation;
	}

	public void setMessageInformation(String messageInformation) {
		this.messageInformation = messageInformation;
	}
	
	public Date getMessageCreationTime() {
		return messageCreationTime;
	}

	public void setMessageCreationTime(Date messageCreationTime) {
		this.messageCreationTime = messageCreationTime;
	}


	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "UserMessage [id=" + id + ", messageInformation=" + messageInformation + ", messageCreationTime="
				+ messageCreationTime + ", userInformation=" + userInformation + "]";
	}

	
}
