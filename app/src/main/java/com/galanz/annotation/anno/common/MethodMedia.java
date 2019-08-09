package com.galanz.annotation.anno.common;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class MethodMedia {

	public static ConcurrentHashMap<Integer, BeanMethod> beanMap;
	static {
		beanMap = new ConcurrentHashMap<Integer, BeanMethod>();
	}
	private static MethodMedia methodMedia;
	private MethodMedia() {}
	public static MethodMedia newInstance() {
		if(methodMedia == null) {
			methodMedia = new MethodMedia();
		}
		return methodMedia;
	}
	public Message process(Message request) throws InvocationTargetException, IllegalAccessException {
		BeanMethod method = beanMap.get(new Integer(request.cmd));
		if (method!=null){
			return (Message) method.getMethod().invoke(method.getBean(),request);
		}
		return null;
	}
}
