package com.ms.learn.bean;

import java.util.List;

public class News {
	
	private List<NewsItem> newsLvItems;
	private List<NewsItem>  newsPagers;
	
	public List<NewsItem> getNewsLvItems() {
		return newsLvItems;
	}
	public void setNewsLvItems(List<NewsItem> newsLvItems) {
		this.newsLvItems = newsLvItems;
	}
	public List<NewsItem> getNewsPagers() {
		return newsPagers;
	}
	public void setNewsPagers(List<NewsItem> newsPagers) {
		this.newsPagers = newsPagers;
	}
}
