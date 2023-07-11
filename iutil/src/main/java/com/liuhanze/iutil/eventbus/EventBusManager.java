package com.liuhanze.iutil.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EventBusManager {

    private static volatile EventBusManager instance;
    private Map<Object,RegisterMethod> methodMap = new HashMap<>();


    public static EventBusManager getInstance(){
        if(instance == null){
            synchronized (EventBusManager.class){
                if(instance == null){
                    instance = new EventBusManager();
                }
            }
        }

        return instance;
    }

    public void postMessage(Object message){
        Set<Object> key = methodMap.keySet();
        Iterator iterator = key.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            RegisterMethod method = methodMap.get(obj);
            invokeMethod(method,message);
        }
    }

    private void invokeMethod(RegisterMethod method,Object message){

        try {
            if(method == null ){
                return ;
            }

            method.getMethod().invoke(method.getObject(),message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    public void register(Object object){
        if(object != null){
            RegisterMethod registerMethod = findMethod(object);
            methodMap.put(object,registerMethod);
        }
    }

    private RegisterMethod findMethod(Object object) {
        if(object != null){
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods){
                EventBus eventBus = method.getAnnotation(EventBus.class);
                if(eventBus != null){

                    Class[] params = method.getParameterTypes();

                    if(params != null && params.length == 1){
                        RegisterMethod registerMethod = new RegisterMethod();
                        registerMethod.setMethod(method);
                        registerMethod.setObject(object);
                        return registerMethod;
                    }

                }
            }
        }

        return null;
    }

    public void unregister(Object object){
        if(object != null && !methodMap.isEmpty()){
            methodMap.remove(object);
        }
    }


}
