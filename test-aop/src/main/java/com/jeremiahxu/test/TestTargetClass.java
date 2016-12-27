package com.jeremiahxu.test;

import java.util.Map;

public class TestTargetClass {

	public String findString(String id, Map<String, String> map) {
		System.out.println("findString");
		String name = map.get("name");
		return id + name;
	}

	public String findTest() {
		System.out.println("findTest");
		return "constants";
	}

	public String findTest2() {
		System.out.println("findTest2");
		return "constants2";
	}
}
