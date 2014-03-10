package com.ms.learn.bean;

public class ExamListEntry {
	
	private String id;
	private String CatogryId;
	private String examName;
	private String examGrade;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatogryId() {
		return CatogryId;
	}
	public void setCatogryId(String catogryId) {
		CatogryId = catogryId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamGrade() {
		return examGrade;
	}
	public void setExamGrade(String examGrade) {
		this.examGrade = examGrade;
	}
	
	

}
