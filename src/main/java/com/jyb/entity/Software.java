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

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 实体类-软件表
 * @author jyb
 *
 */
@Entity
@Table(name="software")
public class Software implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue
	private Integer id; // 主键id

	@NotEmpty(message = "请输入名称！")
	@Column(length = 30)
	private String name; // 软件名称
	
	@NotEmpty(message = "请输入标题！")
	@Column(length = 100)
	private String title; //软件标题  

	@Lob
	@Column(columnDefinition = "longtext")
	private String resourcesDescribe; // 软件描述

	private Integer downloadFrequency; // 软件下载次数

	private Integer browseFrequency; // 软件浏览次数

	private String picture;// 封面

	private String downloadAddress;// 软件下载地址-百度云 其他地址 
	
	private String linkPwd; //资源链接密码-百度云

	private Integer downloadType;//资源地址类别    1.百度云盘地址  2.其他地址 
	
	@Column(length = 500)
	private String remarks;// 备注

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationTime;// 创建时间

	@ManyToOne
	@JoinColumn(name="typeId")
	private DataDictionary dataDictionary; // 所属类别(1电脑软件 2安卓软件 3苹果软件  ) 软件类为B
	
	private String  largeCategory="B";//所属大类别 
	
	private Integer integral;// 积分

	private Integer auditStatus;// 审核状态 （1待审核 2审核通过 3审核未通过）
	
	@Column(length = 200)
    private String reason;// 审核未通过原因
	
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date auditDate;// 审核日期
	
	private Integer isUseful; // 资源链接是否有效  true(1) 有效   false(2) 无效 

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInformation userInformation;// 所属用户

	
	public Software() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Software(Integer auditStatus, Integer isUseful) {
		super();
		this.auditStatus = auditStatus;
		this.isUseful = isUseful;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	 
	public Integer getDownloadFrequency() {
		return downloadFrequency;
	}

	public void setDownloadFrequency(Integer downloadFrequency) {
		this.downloadFrequency = downloadFrequency;
	}

	public Integer getBrowseFrequency() {
		return browseFrequency;
	}

	public void setBrowseFrequency(Integer browseFrequency) {
		this.browseFrequency = browseFrequency;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDownloadAddress() {
		return downloadAddress;
	}

	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}

	public String getLinkPwd() {
		return linkPwd;
	}

	public void setLinkPwd(String linkPwd) {
		this.linkPwd = linkPwd;
	}

	public Integer getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}

	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}

	public String getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getIsUseful() {
		return isUseful;
	}

	public void setIsUseful(Integer isUseful) {
		this.isUseful = isUseful;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	public String getResourcesDescribe() {
		return resourcesDescribe;
	}

	public void setResourcesDescribe(String resourcesDescribe) {
		this.resourcesDescribe = resourcesDescribe;
	}

	@Override
	public String toString() {
		return "Software [id=" + id + ", name=" + name + ", title=" + title + ", resourcesDescribe=" + resourcesDescribe
				+ ", downloadFrequency=" + downloadFrequency + ", browseFrequency=" + browseFrequency + ", picture="
				+ picture + ", downloadAddress=" + downloadAddress + ", linkPwd=" + linkPwd + ", downloadType="
				+ downloadType + ", remarks=" + remarks + ", creationTime=" + creationTime + ", dataDictionary="
				+ dataDictionary + ", largeCategory=" + largeCategory + ", integral=" + integral + ", auditStatus="
				+ auditStatus + ", reason=" + reason + ", auditDate=" + auditDate + ", isUseful=" + isUseful
				+ ", userInformation=" + userInformation + "]";
	}
	
}
