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
private Integer linkId;

@Column(length=30)
private String linkName;

@Column(length=300)
private String linkAddress;

public Integer getLinkId() {
	return linkId;
}

public void setLinkId(Integer linkId) {
	this.linkId = linkId;
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
	return "FriendshipLink [linkId=" + linkId + ", linkName=" + linkName + ", linkAddress=" + linkAddress + "]";
}


}
