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
	private Integer gameId; // 主键id

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

	@Column(length = 200)
	private String gamePicture;// 游戏封面


	@Column(length = 200)
	private String gameDownloadAddress1;// 游戏下载地址-百度云
	
	@Column(length = 100)
	private String linkPwd; //资源链接密码-百度云
	
	@Column(length = 200)
	private String gameDownloadAddress2;// 游戏下载地址-迅雷

	@Column(length = 500)
	private String remarks;// 备注

	private Date gameCreationTime;// 创建时间

	@ManyToOne
	@JoinColumn(name="gameTypeId")
	private DataDictionary dataDictionary; // 所属类别(5单机游戏 6网络游戏 7手游) 游戏类为A
	
	
	private Integer integral;// 积分

	private Integer auditStatus;// 审核状态 （1待审核 2审核通过 3审核未通过）
	
	@Column(length = 200)
    private String reason;// 审核未通过原因

	private Date auditDate;// 审核日期

	@ManyToOne
	@JoinColumn(name = "foreignKeyUserId")
	private UserInformation userInformation;// 所属用户

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
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


	public String getGameDownloadAddress1() {
		return gameDownloadAddress1;
	}

	public void setGameDownloadAddress1(String gameDownloadAddress1) {
		this.gameDownloadAddress1 = gameDownloadAddress1;
	}

	public String getGameDownloadAddress2() {
		return gameDownloadAddress2;
	}

	public void setGameDownloadAddress2(String gameDownloadAddress2) {
		this.gameDownloadAddress2 = gameDownloadAddress2;
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

	@Override
	public String toString() {
		return "GameInformation [gameId=" + gameId + ", gameName=" + gameName + ", gameTitle=" + gameTitle
				+ ", gameDescribe=" + gameDescribe + ", gameDownloadFrequency=" + gameDownloadFrequency
				+ ", gameBrowseFrequency=" + gameBrowseFrequency + ", gamePicture=" + gamePicture
				+ ", gameDownloadAddress1=" + gameDownloadAddress1 + ", linkPwd=" + linkPwd + ", gameDownloadAddress2="
				+ gameDownloadAddress2 + ", remarks=" + remarks + ", gameCreationTime=" + gameCreationTime
				+ ", dataDictionary=" + dataDictionary + ", integral=" + integral + ", auditStatus=" + auditStatus
				+ ", reason=" + reason + ", auditDate=" + auditDate + ", userInformation=" + userInformation + "]";
	}


}
