package com.ms.learn.bean;

public class ExamInfoEntry {
	
	private String title;
	private String choic_A;
	private String choic_B;
	private String choic_C;
	private String choic_D;
	private String answer;
	private String grad;
	
	private boolean[] result=new boolean[4];
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChoic_A() {
		return choic_A;
	}
	public void setChoic_A(String choic_A) {
		this.choic_A = choic_A;
	}
	public String getChoic_B() {
		return choic_B;
	}
	public void setChoic_B(String choic_B) {
		this.choic_B = choic_B;
	}
	public String getChoic_C() {
		return choic_C;
	}
	public void setChoic_C(String choic_C) {
		this.choic_C = choic_C;
	}
	public String getChoic_D() {
		return choic_D;
	}
	public void setChoic_D(String choic_D) {
		this.choic_D = choic_D;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public boolean[] getResult() {
		return result;
	}
	public void setResult(boolean[] result) {
		this.result = result;
	}
	

}
