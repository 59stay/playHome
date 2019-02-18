package com.jyb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实体类-用户签到表
 * @author jyb
 *
 */
@Entity
@Table(name = "sign_in")
public class SignIn {
@Id
@GeneratedValue	
private  Integer id; //编号

@ManyToOne
@JoinColumn(name="userId")
private UserInformation userInformation;  //签到用户

@JSONField(format="yyyy-MM-dd HH:mm:ss")
private String signInTime ;//签到时间

private Integer signInRanking;//签到排名

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public UserInformation getUserInformation() {
	return userInformation;
}

public void setUserInformation(UserInformation userInformation) {
	this.userInformation = userInformation;
}


public String getSignInTime() {
	return signInTime;
}

public void setSignInTime(String signInTime) {
	this.signInTime = signInTime;
}

public Integer getSignInRanking() {
	return signInRanking;
}

public void setSignInRanking(Integer signInRanking) {
	this.signInRanking = signInRanking;
}

@Override
public String toString() {
	return "SignIn [id=" + id + ", userInformation=" + userInformation + ", signInTime=" + signInTime
			+ ", signInRanking=" + signInRanking + "]";
}



}
