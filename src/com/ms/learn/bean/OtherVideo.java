package com.ms.learn.bean;

import java.io.Serializable;

public class OtherVideo  implements  Serializable{
	
	private static final long serialVersionUID = 1L;
	private String  VideoID;
	private String  VideoName;
	private String  VideoTitle;
	private String  VideoUrl;
	private String  VideoDecr;
	private String  VideoVid;
	private String  videoAllowDn;
		
	public String getVideoName() {
		return VideoName;
	}
	public void setVideoName(String videoName) {
		VideoName = videoName;
	}
	public String getVideoUrl() {
		return VideoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		VideoUrl = videoUrl;
	}
	public String getVideoID() {
		return VideoID;
	}
	public void setVideoID(String videoID) {
		VideoID = videoID;
	}
	public String getVideoDecr() {
		return VideoDecr;
	}
	public void setVideoDecr(String videoDecr) {
		VideoDecr = videoDecr;
	}
	public String getVideoVid() {
		return VideoVid;
	}
	public void setVideoVid(String videoVid) {
		VideoVid = videoVid;
	}
	public String getVideoAllowDn() {
		return videoAllowDn;
	}
	public void setVideoAllowDn(String videoAllowDn) {
		this.videoAllowDn = videoAllowDn;
	}
	public String getVideoTitle() {
		return VideoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		VideoTitle = videoTitle;
	}
}
