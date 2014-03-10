package com.ms.learn.bean;

public class OfficeDetailItem {
	/*
    documentInfo 
    <id><cid> dowCount<filename><filedescribe>fileurl

	 */
	
	private String id;
	private String catetoryId;
	private String downCount;
	private String fileName;
	private String fileUrl;
	private String fileDescribe;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatetoryId() {
		return catetoryId;
	}
	public void setCatetoryId(String catetoryId) {
		this.catetoryId = catetoryId;
	}
	public String getDownCount() {
		return downCount;
	}
	public void setDownCount(String downCount) {
		this.downCount = downCount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileDescribe() {
		return fileDescribe;
	}
	public void setFileDescribe(String fileDescribe) {
		this.fileDescribe = fileDescribe;
	}
	
	

}
