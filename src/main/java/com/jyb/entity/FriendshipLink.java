package com.jyb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="friendship_link")
public class FriendshipLink {
@Id
@GeneratedValue
private Integer id;

@Column(length=30)
private String linkName;

@Column(length=300)
private String linkAddress;

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getLinkName() {
	return linkName;
}
public void setLinkName(String linkName) {
	this.linkName = linkName;
}

public String getLinkAddress() {
	return linkAddress;
}

public void setLinkAddress(String linkAddress) {
	this.linkAddress = linkAddress;
}

@Override
public String toString() {
	return "FriendshipLink [id=" + id + ", linkName=" + linkName + ", linkAddress=" + linkAddress + "]";
}



}
