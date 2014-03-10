package com.ms.learn.bean;

import java.util.List;


public class GalleryItem {
	
	private String categoryID;
	private String categoryName;
	private List<VideoInfo> VideoInfos;
	

	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<VideoInfo> getVideoInfos() {
		return VideoInfos;
	}
	public void setVideoInfos(List<VideoInfo> videoInfos) {
		VideoInfos = videoInfos;
	}
	
	

	
}
