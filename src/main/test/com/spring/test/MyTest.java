package com.spring.test;

import com.spring.classPathXml.ClassPathXmlApplicationContext;
import com.spring.service.StudentService;

public class MyTest {

	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		StudentService student = (StudentService) context.getBean("StudentService");
		student.getStudent().introduce();
	}

}
