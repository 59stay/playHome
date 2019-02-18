package com.jyb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;


/**
 * 实体类-下载记录表
 * @author jyb
 *
 */
@Entity
@Table(name = "download_record")
public class DownloadRecord {
	
	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	private Integer resourceId;// 资源id
	
	private String resourceName;// 资源名称
	
	private String largeCategory;// 大类别
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserInformation userInformation; // 下载用户
	
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date downloadDate; // 下载日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}



	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}
	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	@Override
	public String toString() {
		return "DownloadRecord [id=" + id + ", resourceId=" + resourceId + ", resourceName=" + resourceName
				+ ", largeCategory=" + largeCategory + ", userInformation=" + userInformation + ", downloadDate="
				+ downloadDate + "]";
	}


	
	
	
}
