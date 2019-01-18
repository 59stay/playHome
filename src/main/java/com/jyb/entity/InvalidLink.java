package com.jyb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;

/**
 * 失效链接（电影 游戏 书籍 软件）
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
	@Column(length = 30)
	private Integer userId;// 用户id
	@Column(length = 30)
	private String invalidName;// 失效链名称
	private Date creationTime; // 失效链接创建时间
	private String largeCategory;// 失效链接大类别
	private Integer resourceId;// 资源id

	private String gameDownloadAddress1;// 游戏下载地址-百度云

	private String linkPwd; // 资源链接密码-百度云

	private String gameDownloadAddress2;// 游戏下载地址-其他地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvalidName() {
		return invalidName;
	}

	public void setInvalidName(String invalidName) {
		this.invalidName = invalidName;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
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

	public String getGameDownloadAddress1() {
		return gameDownloadAddress1;
	}

	public void setGameDownloadAddress1(String gameDownloadAddress1) {
		this.gameDownloadAddress1 = gameDownloadAddress1;
	}

	public String getLinkPwd() {
		return linkPwd;
	}

	public void setLinkPwd(String linkPwd) {
		this.linkPwd = linkPwd;
	}

	public String getGameDownloadAddress2() {
		return gameDownloadAddress2;
	}

	public void setGameDownloadAddress2(String gameDownloadAddress2) {
		this.gameDownloadAddress2 = gameDownloadAddress2;
	}

	@Override
	public String toString() {
		return "InvalidLink [id=" + id + ", userId=" + userId + ", invalidName=" + invalidName + ", creationTime="
				+ creationTime + ", largeCategory=" + largeCategory + ", resourceId=" + resourceId
				+ ", gameDownloadAddress1=" + gameDownloadAddress1 + ", linkPwd=" + linkPwd + ", gameDownloadAddress2="
				+ gameDownloadAddress2 + "]";
	}

}
