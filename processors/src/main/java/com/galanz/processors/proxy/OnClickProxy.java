package com.galanz.processors.proxy;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.galanz.processors.anno.Event;
import com.galanz.processors.anno.OnClick;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class OnClickProxy {

    private OnClickProxy(){}
    private static OnClickProxy instance = null;
    public static OnClickProxy newInstance(){
        if (instance==null){
            instance = new OnClickProxy();
        }
        return instance;
    }
    public void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();//获取到类
        Method[] methods = clazz.getDeclaredMethods();//获取到该activity类下所有的公有或者私有的方法,不包括父类
        for (Method method : methods) {//对方法一个一个遍历
            Annotation[] annotations = method.getAnnotations();//获取到方法上所有的注解,因为有可能有多个注解,比如上注解上加注解
            for (Annotation annotation : annotations) {//遍历获取到的注解集合
                Class<? extends Annotation> annotationType = annotation.annotationType();//获取到注解上的类型
                if (annotationType != null) {
                    Event event = annotationType.getAnnotation(Event.class);//获取到注解上的注解,如果是我们自己写的@Event的时候往下走,不是则下一循环
                    if (event != null) {
                        //获取到了注解上的注解的一些参数
                        String listenerSetter = event.listenerSetter(); // setOnClickListener
                        String callBackListener = event.callBackListener(); // onClick
                        Class<?> listenerType = event.listenerType();// interface android.view.View$OnClickListener
                        //接下来就是要实现我们怎么动态代理view里面的点击事件了
                        //创建一个带有目标activity对象的代理handler  将我们对象里面的method 方法进行添加
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(callBackListener, method);
                        //这句代码是获取到了View.OnclickListener对象
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);//比如每当我们调用了listenerType里面的方法时,就会进入到handler类里的invoke方法里.
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");//annotationType就是我们定义的@OnClick注解接口 这名话的意思就是获取到接口里面的value方法
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);//通过反射获取到的valueMethod方法来获取到返回的数据,anotation就是我们的注解类的实例
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
//                                view.setOnClickListener((View.OnClickListener) listener);
                                Method m = view.getClass().getMethod(listenerSetter, listenerType);//获取到View.setOnclickListener()方法
                                m.invoke(view, listener);//执行的是view.setOnClickListener(new View.OnClickListener(){ onClick();})
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    //动态代理其实就是实现了InvocationHandler接口,并实现里面的invoke方法来实现的
    public class ListenerInvocationHandler implements InvocationHandler {
        private Object target;
        private HashMap<String, Method> methodMethod = new HashMap();
        public ListenerInvocationHandler(Object target) {
            this.target = target;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (target != null) {
                String methodName = method.getName();
                Method targetMethod = methodMethod.get(methodName);
                if (targetMethod != null) {
                    return targetMethod.invoke(target, args);
                }
            }
            return null;
        }
        public void addMethod(String methodName, Method method) {
            methodMethod.put(methodName, method);
        }
    }


    public void injectOnClickEvents(final Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)){
                View.OnClickListener listener = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        try {
                            method.invoke(activity,view);
                        } catch (Exception e) {
                        }
                    }
                };
                int ids[] = method.getAnnotation(OnClick.class).value();
                for (int viewId : ids) {
                    View view = activity.findViewById(viewId);
                    view.setOnClickListener(listener);
                }
            }
        }
    }
}
