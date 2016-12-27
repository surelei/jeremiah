package com.jeremiahxu.util.log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeremiahxu.util.log.service.LogUtilService;

public class LogUtilRunner {

	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:test.xml");
		LogUtilService logUtilService = (LogUtilService) context.getBean("logUtilService");
		logUtilService.parseLog();
		System.out.println("done.");
	}

}
