package com.ms.learn.bean;

public class CommendItem {
	//commend Item
	private String  ID;
	private String vid;
	private String commendMsg;
	private String uID;
	private String uTime;
	private String uName;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getCommendMsg() {
		return commendMsg;
	}
	public void setCommendMsg(String commendMsg) {
		this.commendMsg = commendMsg;
	}
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public String getuTime() {
		return uTime;
	}
	public void setuTime(String uTime) {
		this.uTime = uTime;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}

	
}
