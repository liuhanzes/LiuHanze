
package com.liuhanze.iutil;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

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
}
