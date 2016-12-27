package com.jeremiahxu.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestTargetClass testClass = (TestTargetClass) factory.getBean("testClass");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xulei");
		String result = testClass.findString("13", map);
		System.out.println(result);
		map.put("name", "leixu");
		result = testClass.findString("0", map);
		System.out.println(result);
		result = testClass.findTest();
		System.out.println(result);
		result = testClass.findTest2();
		System.out.println(result);
	}
}
