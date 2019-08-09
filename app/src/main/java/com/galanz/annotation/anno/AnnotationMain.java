package com.galanz.annotation.anno;


import com.alibaba.fastjson.JSON;
import com.galanz.annotation.anno.common.BeanClass;
import com.galanz.annotation.anno.common.BeanMethod;
import com.galanz.annotation.anno.common.ClassMedia;
import com.galanz.annotation.anno.common.Message;
import com.galanz.annotation.anno.common.MethodMedia;
import com.galanz.annotation.utils.PkgUtil;
import com.galanz.processors.anno.CMD;
import com.galanz.processors.anno.Controller;


import java.lang.reflect.Method;
import java.util.*;

public class AnnotationMain {

    static List<Message> mes = new ArrayList<Message>();
    public static void main(String args[]) throws Exception {
        Set<Class<?>> classes =  PkgUtil.getClzFromPkg("com.galanz.annotation.anno.controller");
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()){
            Class c = iterator.next();
            annotation(c);
//            annotationClass(c);
        }
        data();//准备数据
        for (Message message:mes){
            Message messback = MethodMedia.newInstance().process(message);
            if (messback != null){
                System.out.println("数据返回："+ JSON.toJSONString(messback));
            }
        }
//        for (Message message:mes){
//            Message messback = ClassMedia.newInstance().process(message);
//            if (messback != null){
//                System.out.println("数据返回："+ JSON.toJSONString(messback));
//            }
//        }
    }

    /**
     * 准备数据
     */
    public static void data() {
        mes.clear();
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            int random = new Random().nextInt(2);
            if (random == 0) {
                message.controller = "device";
                switch (i % 4) {
                    case 0:
                        message.cmd = 1010;
                        message.data = "register";
                        break;
                    case 1:
                        message.cmd = 1011;
                        message.data = "login";
                        break;
                    case 2:
                        message.cmd = 1012;
                        message.data = "logout";
                        break;
                    case 3:
                        message.cmd = 1013;
                        message.data = "stateUp";
                        break;
                }
            } else {
                message.controller = "user";
                switch (i % 3) {
                    case 0:
                        message.cmd = 2010;
                        message.data = "register";
                        break;
                    case 1:
                        message.cmd = 2011;
                        message.data = "login";
                        break;
                    case 2:
                        message.cmd = 2012;
                        message.data = "logout";
                        break;
                }
            }
            mes.add(message);
        }
    }


    /**
     * 解析这个类是否携带有注解，并保存在集合中
     * @param c
     */
    public static void annotation(Class c) throws IllegalAccessException, InstantiationException {
        if(c.isAnnotationPresent(Controller.class)) {
            Method method[] = c.getMethods();
            for(Method m:method) {
                if(m.isAnnotationPresent(CMD.class)) {
                    BeanMethod beanMethod = new BeanMethod();
                    beanMethod.setBean(c.newInstance());
                    beanMethod.setMethod(m);
                    int cmd = m.getAnnotation(CMD.class).value();
                    MethodMedia.beanMap.put(new Integer(cmd), beanMethod);
                }
            }
        }
    }
    public static void annotationClass(Class c) throws IllegalAccessException, InstantiationException {
        Object o = c.newInstance();
        if (c.isAnnotationPresent(Controller.class)) {
            BeanClass beanClass = new BeanClass();
            beanClass.setBean(o);
            Method method[] = c.getMethods();
            for (Method m : method) {
                if (m.isAnnotationPresent(CMD.class)) {
                    BeanMethod beanMethod = new BeanMethod();
                    beanMethod.setBean(o);
                    beanMethod.setMethod(m);
                    int cmd = m.getAnnotation(CMD.class).value();
                    beanClass.addMethod(new Integer(cmd), beanMethod);
                }
            }
            Controller controller = (Controller) c.getAnnotation(Controller.class);
            ClassMedia.beanMap.put(controller.value(), beanClass);
        }
    }
}


