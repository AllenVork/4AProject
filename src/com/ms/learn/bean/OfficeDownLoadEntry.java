package com.ms.learn.bean;

import java.util.List;

public class OfficeDownLoadEntry {
	
	private String parenFileName;
	private String parenFilePath;
	
	private List<DownFileItem> downFileItems;
	public String getParenFileName() {
		return parenFileName;
	}
	public void setParenFileName(String parenFileName) {
		this.parenFileName = parenFileName;
	}
	public List<DownFileItem> getDownFileItems() {
		return downFileItems;
	}
	public void setDownFileItems(List<DownFileItem> downFileItems) {
		this.downFileItems = downFileItems;
	}
	public String getParenFilePath() {
		return parenFilePath;
	}
	public void setParenFilePath(String parenFilePath) {
		this.parenFilePath = parenFilePath;
	}
	
	

}
