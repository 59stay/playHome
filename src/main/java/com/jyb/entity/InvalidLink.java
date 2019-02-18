package com.jyb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;

/**
 * 失效链接（电影 游戏 软件）
 * 
 * @author jyb
 *
 */
@Entity
@Table(name = "invalid_link")
public class InvalidLink {
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer userId;// 用户id
	
	private String userName;//用户名称
	
	private String invalidName;// 失效链名称
	
	private Integer downloadType;//资源地址类别    1.百度云盘地址  2.其他地址 
	
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationTime; // 失效链接创建时间
	
	private String largeCategory;// 失效链接大类别
	
	private Integer resourceId;// 资源id
	
	private String downloadAddress;// 游戏下载地址-百度云 或其他
	
	private String linkPwd; // 资源链接密码-百度云

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInvalidName() {
		return invalidName;
	}

	public void setInvalidName(String invalidName) {
		this.invalidName = invalidName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLinkPwd() {
		return linkPwd;
	}

	public void setLinkPwd(String linkPwd) {
		this.linkPwd = linkPwd;
	}

	public String getDownloadAddress() {
		return downloadAddress;
	}

	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}

	public Integer getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}

	@Override
	public String toString() {
		return "InvalidLink [id=" + id + ", userId=" + userId + ", userName=" + userName + ", invalidName="
				+ invalidName + ", downloadType=" + downloadType + ", creationTime=" + creationTime + ", largeCategory="
				+ largeCategory + ", resourceId=" + resourceId + ", downloadAddress=" + downloadAddress + ", linkPwd="
				+ linkPwd + "]";
	}

 

}
