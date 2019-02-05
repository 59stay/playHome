package com.jyb.entity;

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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;
@Entity
@Table(name="game_information")
public class GameInformation {
	@Id
	@GeneratedValue
	private Integer id; // 主键id

	@NotEmpty(message = "请输入名称！")
	@Column(length = 30)
	private String gameName; // 游戏名称
	
	@NotEmpty(message = "请输入标题！")
	@Column(length = 100)
	private String gameTitle; //游戏标题  

	@Lob
	@Column(columnDefinition = "longtext")
	private String gameDescribe; // 游戏描述

	private Integer gameDownloadFrequency; // 游戏下载次数

	private Integer gameBrowseFrequency; // 游戏浏览次数

	private String gamePicture;// 游戏封面

	private String gameDownloadAddress;// 游戏下载地址-百度云 其他地址 
	
	private String linkPwd; //资源链接密码-百度云

	private Integer downloadType;//资源地址类别    1.百度云盘地址  2.其他地址 
	
	@Column(length = 500)
	private String remarks;// 备注

	private Date gameCreationTime;// 创建时间

	@ManyToOne
	@JoinColumn(name="gameTypeId")
	private DataDictionary dataDictionary; // 所属类别(1单机游戏 2网络游戏  ) 游戏类为A
	
	private String  largeCategory="A";//所属大类别 
	
	private Integer integral;// 积分

	private Integer auditStatus;// 审核状态 （1待审核 2审核通过 3审核未通过）
	
	@Column(length = 200)
    private String reason;// 审核未通过原因

	private Date auditDate;// 审核日期
	
	private boolean isUseful=true; // 资源链接是否有效 true(0) 有效 false(1) 无效 默认有效

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInformation userInformation;// 所属用户
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameDescribe() {
		return gameDescribe;
	}

	public void setGameDescribe(String gameDescribe) {
		this.gameDescribe = gameDescribe;
	}

	public Integer getGameDownloadFrequency() {
		return gameDownloadFrequency;
	}

	public void setGameDownloadFrequency(Integer gameDownloadFrequency) {
		this.gameDownloadFrequency = gameDownloadFrequency;
	}

	public Integer getGameBrowseFrequency() {
		return gameBrowseFrequency;
	}

	public void setGameBrowseFrequency(Integer gameBrowseFrequency) {
		this.gameBrowseFrequency = gameBrowseFrequency;
	}

	
	public String getGamePicture() {
		return gamePicture;
	}

	public void setGamePicture(String gamePicture) {
		this.gamePicture = gamePicture;
	}



	public String getGameDownloadAddress() {
		return gameDownloadAddress;
	}

	public void setGameDownloadAddress(String gameDownloadAddress) {
		this.gameDownloadAddress = gameDownloadAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getGameCreationTime() {
		return gameCreationTime;
	}

	public void setGameCreationTime(Date gameCreationTime) {
		this.gameCreationTime = gameCreationTime;
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

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	
	public String getLinkPwd() {
		return linkPwd;
	}

	public void setLinkPwd(String linkPwd) {
		this.linkPwd = linkPwd;
	}

	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}

	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isUseful() {
		return isUseful;
	}

	public void setUseful(boolean isUseful) {
		this.isUseful = isUseful;
	}

	public String getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}

	public Integer getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}

	@Override
	public String toString() {
		return "GameInformation [id=" + id + ", gameName=" + gameName + ", gameTitle=" + gameTitle + ", gameDescribe="
				+ gameDescribe + ", gameDownloadFrequency=" + gameDownloadFrequency + ", gameBrowseFrequency="
				+ gameBrowseFrequency + ", gamePicture=" + gamePicture + ", gameDownloadAddress=" + gameDownloadAddress
				+ ", linkPwd=" + linkPwd + ", downloadType=" + downloadType + ", remarks=" + remarks
				+ ", gameCreationTime=" + gameCreationTime + ", dataDictionary=" + dataDictionary + ", largeCategory="
				+ largeCategory + ", integral=" + integral + ", auditStatus=" + auditStatus + ", reason=" + reason
				+ ", auditDate=" + auditDate + ", isUseful=" + isUseful + ", userInformation=" + userInformation + "]";
	}


 
	





}
