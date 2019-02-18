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

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyb.util.CustomDateSerializer;
import com.jyb.util.CustomDateTimeSerializer;
/**
 * 实体类-用户评论
 * @author jyb
 *
 */
@Entity
@Table(name="user_reviews ")
public class UserReviews {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserInformation userInformation; //评论人
	
	@Column(length=30)
	private String resourceName;  //资源名称
	
	private Integer resourceId;  //资源Id
	
	@Column(length=30)
	private String largeCategory;  //大类别
	
	 @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date reviewsTime;  //评论时间
    
    @Column(length=500)
    private String reviewsContent;//评论内容

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

	public String getLargeCategory() {
		return largeCategory;
	}

	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}
	
	public Date getReviewsTime() {
		return reviewsTime;
	}

	public void setReviewsTime(Date reviewsTime) {
		this.reviewsTime = reviewsTime;
	}

	public String getReviewsContent() {
		return reviewsContent;
	}

	public void setReviewsContent(String reviewsContent) {
		this.reviewsContent = reviewsContent;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	 

	
	
}
