package com.galanz.annotation.anno.common;

import java.util.HashMap;
import java.util.Map;

public class BeanClass {
    private Object bean;
    private Map<Integer, BeanMethod> beanMaps = new HashMap<>();
    public void addMethod(Integer key , BeanMethod method){
        beanMaps.put(key,method);
    }
    public BeanMethod getBeanMethod(int cmd){
        return beanMaps.get(cmd);
    }
    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }
}
