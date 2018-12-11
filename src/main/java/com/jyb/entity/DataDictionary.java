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
	private Integer dictionaryId; // 主键id

	@Column(length = 30)
	private String dictionaryName;// 字典名称

	private Integer dictionaryType; // 字典类型 (1游戏 2电影)

	private Integer dictionarySort; // 字典排序

	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public Integer getDictionaryType() {
		return dictionaryType;
	}

	public void setDictionaryType(Integer dictionaryType) {
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
		return "DataDictionary [dictionaryId=" + dictionaryId + ", dictionaryName=" + dictionaryName
				+ ", dictionaryType=" + dictionaryType + ", dictionarySort=" + dictionarySort + "]";
	}

}
