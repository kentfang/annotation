package com.galanz.processors.anno;

import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Event(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, callBackListener = "onClick") //上面写的注解类,注解在注解之上的)
public @interface OnClick {
    int[] value();
}
