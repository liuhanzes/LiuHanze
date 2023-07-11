package com.liuhanze.iutil.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义简版eventBus
 * @author LiuHanze
 * @date 2023/6/29
 *
 * use like this
 *
 * mark method where you need receive message
 * @EventBus
 * public void onMessage(String message){
 *
 *     do some this here....
 * }
 *
 * send message
 * EventBusManager.getInstance().postMessage("this is message");
 *
 * register before use
 * EventBusManager.getInstance().register(this);
 * when onDestroy please unregister
 * EventBusManager.getInstance().unregister(this);
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventBus {

}
