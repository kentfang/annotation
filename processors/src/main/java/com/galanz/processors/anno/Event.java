package com.galanz.processors.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)//放在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    String listenerSetter();// 监听的方法名 比如: setOnClickListener()
    Class<?> listenerType(); // 获取到需要监听的类 比如: View.OnClickListener
    String callBackListener();//比如 onClick()
}

