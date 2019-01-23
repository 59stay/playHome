package com.jyb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_dictionary")
public class DataDictionary {
	@Id
	@GeneratedValue
	private Integer id; // 主键id

	@Column(length = 30)
	private String dictionaryName;// 字典名称类型
	
	@Column(length = 30)
	private String dictionaryType; // 字典大类型 (A游戏 B电影)

	private Integer dictionarySort; // 字典排序



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getDictionaryType() {
		return dictionaryType;
	}

	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}

	public Integer getDictionarySort() {
		return dictionarySort;
	}

	public void setDictionarySort(Integer dictionarySort) {
		this.dictionarySort = dictionarySort;
	}

	@Override
	public String toString() {
		return "DataDictionary [id=" + id + ", dictionaryName=" + dictionaryName + ", dictionaryType=" + dictionaryType
				+ ", dictionarySort=" + dictionarySort + "]";
	}

 

}
