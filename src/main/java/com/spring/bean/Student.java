package com.spring.bean;

public class Student {

	private String name;
    private String grade;
    private String major;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * 自我介绍的方法
	 */
	public void introduce(){
		System.out.println("我是叫"+name+",是一名"+major+"专业的"+grade+"学生!");
	}
}
