package com.ms.learn.bean;

import java.util.List;


public class VideoDetailInfo {
	
	private String VideoDetail;
	private String videoID;
	private String titleName;
	
	private List<OtherVideo> otherVideos;
	
	public String getVideoDetail() {
		return VideoDetail;
	}
	public void setVideoDetail(String videoDetail) {
		VideoDetail = videoDetail;
	}
	public List<OtherVideo> getOtherVideos() {
		return otherVideos;
	}
	public void setOtherVideos(List<OtherVideo> otherVideos) {
		this.otherVideos = otherVideos;
	}
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
