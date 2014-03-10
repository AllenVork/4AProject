package com.ms.learn.bean;

import java.util.List;

public class PostsDetailEntry {
	
	private List<PostsReciveEnrty>  postsReciveEnrties;
	private String contentInfo;
	public List<PostsReciveEnrty> getPostsReciveEnrties() {
		return postsReciveEnrties;
	}
	public void setPostsReciveEnrties(List<PostsReciveEnrty> postsReciveEnrties) {
		this.postsReciveEnrties = postsReciveEnrties;
	}
	public String getContentInfo() {
		return contentInfo;
	}
	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
	}


}
