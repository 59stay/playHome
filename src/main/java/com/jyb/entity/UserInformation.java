package com.jyb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateTimeSerializer;
/**
 * 用户信息实体类
 * @author jyb
 *
 */
@Entity
@Table(name="user_information")
public class UserInformation {
	@Id
	@GeneratedValue
	private Integer id;  // id
	
	@Column(length=30)
	@NotEmpty(message="请输入用户名称！")
	private String userName; // 用户名称
	
	private String sex; // 性别  1.男  2.女
	
	@NotEmpty(message="请输入密码！")
	@Column(length=100)
	private String userPassword; // 密码
	
	@Column(length=100)
	private String userHead; // 用户头像
	
	@Email(message="邮箱地址格式有误！")
	@NotEmpty(message="请输入邮箱地址！")
	@Column(length=30)
	private String email; // 验证邮箱地址
	
	@Column(length=11)
	private String userPhoneNumber; // 用户手机号
	
	@Column(length=30)
	private String userIdcard; // 用户身份证号
	
	private Integer accountStatus; // 账号状态 0表示可用 1表示禁用 （默认为0）
	
	private Integer userRole; // 用户角色    0表示管理员 1表示普通用户 （默认为1）
	
	@Column(length=100)
	private String remarks; // 备注
	
    private Integer userIntegral;//用户积分
	
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date userCreationTime; // 创建时间
	
	@Column(length=30)
	private String hostIp;

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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserIdcard() {
		return userIdcard;
	}

	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(Integer userIntegral) {
		this.userIntegral = userIntegral;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUserCreationTime() {
		return userCreationTime;
	}

	public void setUserCreationTime(Date userCreationTime) {
		this.userCreationTime = userCreationTime;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "UserInformation [id=" + id + ", userName=" + userName + ", sex=" + sex + ", userPassword="
				+ userPassword + ", userHead=" + userHead + ", email=" + email + ", userPhoneNumber=" + userPhoneNumber
				+ ", userIdcard=" + userIdcard + ", accountStatus=" + accountStatus + ", userRole=" + userRole
				+ ", remarks=" + remarks + ", userIntegral=" + userIntegral + ", userCreationTime=" + userCreationTime
				+ ", hostIp=" + hostIp + "]";
	}



	


}
