package com.spring.service;

import com.spring.bean.Student;
import com.spring.classPathXml.ClassPathXmlApplicationContext;

public class MyTest {

	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//		Student student = (Student) context.getBean("student");
//		System.out.println(student.getName()+":"+student.getGrade());
		StudentService service = (StudentService)context.getBean("studentService");
		Student st = service.getStudent();
		System.out.println(st.getName());
		service.getStudent().introduce();
		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));
		System.out.println(System.getProperty("java.class.path"));
	}

}
