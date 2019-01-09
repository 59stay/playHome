package com.jyb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;

@Entity
@Table(name="user_message")
public class UserMessage {

	@Id
	@GeneratedValue
	private Integer messageId;  // id
	
	@Column(length=500)
	private String messageInformation; // 留言信息
	
	private Date  messageCreationTime;//留言时间
	
	@Column(length=30)
	private String keyUserName; //用户昵称
	
	@Column(length=100)
	private String userHead; //用户头像

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getMessageInformation() {
		return messageInformation;
	}

	public void setMessageInformation(String messageInformation) {
		this.messageInformation = messageInformation;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getMessageCreationTime() {
		return messageCreationTime;
	}

	public void setMessageCreationTime(Date messageCreationTime) {
		this.messageCreationTime = messageCreationTime;
	}

	public String getKeyUserName() {
		return keyUserName;
	}

	public void setKeyUserName(String keyUserName) {
		this.keyUserName = keyUserName;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	@Override
	public String toString() {
		return "UserMessage [messageId=" + messageId + ", messageInformation=" + messageInformation
				+ ", messageCreationTime=" + messageCreationTime + ", keyUserName=" + keyUserName + ", userHead="
				+ userHead + "]";
	}
	
	
	
	
	
}
