package com.ms.learn.bean;

import java.util.List;

public class OfficeCategorylist {
	
	private OfficeCategoryItem categoryItem;
	
	private List<OfficeCategoryItem> categoryItems;
	
	
	public OfficeCategoryItem getCategoryItem() {
		return categoryItem;
	}
	public void setCategoryItem(OfficeCategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	public List<OfficeCategoryItem> getCategoryItems() {
		return categoryItems;
	}
	public void setCategoryItems(List<OfficeCategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}
	
	

}
