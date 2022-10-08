
package com.liuhanze.iutil;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

public final class IUtil {

    private static Application mContext;
    /**
     * main thread Handler
     */
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void init(Application application){
        mContext = application;
    }

    public static Context getContext(){
        if (mContext == null) {
            throw new ExceptionInInitializerError("context is null , must use AXUtil.init() first！");
        }
        return mContext;
    }

    /**
     * get main Handler
     *
     * @return Handler
     */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }
    /**
     * 在主线程中执行
     *
     * @param runnable
     * @return
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return getMainHandler().post(runnable);
    }

    /**
     * 获取系统服务
     *
     * @param name  服务名
     * @param clazz 服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(String name, Class<T> clazz) {
        return getSystemService(getContext(), name, clazz);
    }

    /**
     * 获取系统服务
     *
     * @param context 上下文
     * @param name    服务名
     * @param clazz   服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(Context context, String name, Class<T> clazz) {
        if (!TextUtils.isEmpty(name) && clazz != null && context != null) {
            Object obj = context.getSystemService(name);
            return clazz.isInstance(obj) ? (T) obj : null;
        } else {
            return null;
        }
    }
}
