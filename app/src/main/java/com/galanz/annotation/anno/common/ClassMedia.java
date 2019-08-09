package com.galanz.annotation.anno.common;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class ClassMedia {

	public static ConcurrentHashMap<String, BeanClass> beanMap;
	static {
		beanMap = new ConcurrentHashMap<String, BeanClass>();
	}
	private static ClassMedia methodMedia;
	private ClassMedia() {}
	public static ClassMedia newInstance() {
		if(methodMedia == null) {
			methodMedia = new ClassMedia();
		}
		return methodMedia;
	}

	public Message process(Message request) throws InvocationTargetException, IllegalAccessException {
		BeanClass beanClass = beanMap.get(request.controller);
		if (null!= beanClass){
			BeanMethod method = beanClass.getBeanMethod(new Integer(request.cmd));
			if (beanClass.getBean()==null){
				System.out.println("null");
			}
			if (method!=null){
				return (Message) method.getMethod().invoke(beanClass.getBean(),request);
			}
		}
		return null;
	}
}
