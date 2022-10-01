package com.liuhanze.demos.application;

import android.app.Application;

import com.liuhanze.iutil.IUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        IUtil.init(this);
    }
}
