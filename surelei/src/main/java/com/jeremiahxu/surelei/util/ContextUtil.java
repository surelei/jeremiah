package com.jeremiahxu.surelei.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Jeremiah Xu
 * 
 */
public class ContextUtil implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return CONTEXT;
	}

	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}

	public static ApplicationContext getContext() {
		return CONTEXT;
	}
}
