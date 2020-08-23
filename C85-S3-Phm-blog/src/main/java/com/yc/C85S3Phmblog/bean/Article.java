package com.yc.C85S3Phmblog.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Article implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	  private String author;
	  private String title;
	  private String content;
	  private String keyWords;
	  private String descript;
	  private Integer categoryId;
	  private String label;
	  private String titleImgs;
	  private String status;
	  private Timestamp createtime;
	  private Integer readcnt;
	  private Integer agreecnt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitleImgs() {
		return titleImgs;
	}
	public void setTitleImgs(String titleImgs) {
		this.titleImgs = titleImgs;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(Integer readcnt) {
		this.readcnt = readcnt;
	}
	public Integer getAgreecnt() {
		return agreecnt;
	}
	public void setAgreecnt(Integer agreecnt) {
		this.agreecnt = agreecnt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	  
	  

}
