package com.liuhanze.demos.application;

import android.app.Application;

import com.liuhanze.demos.BuildConfig;
import com.liuhanze.iutil.IUtil;
import com.liuhanze.iutil.log.ILog;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        IUtil.init(this);
        ILog.isDebug(BuildConfig.DEBUG);
    }
}
