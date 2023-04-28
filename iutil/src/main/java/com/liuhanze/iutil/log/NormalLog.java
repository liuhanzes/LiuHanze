package com.liuhanze.iutil.log;

import androidx.annotation.NonNull;

public class NormalLog extends LogDecorator {

    private static volatile NormalLog instance = null;


    private NormalLog(){

    }

    public static NormalLog getInstance(){
        if(instance == null){
            synchronized (NormalLog.class){
                if(instance == null){
                    instance = new NormalLog();
                }
            }
        }

        return instance;
    }


    @Override
    protected void makeLog(int type, @NonNull String tag, @NonNull String text) {
        super.makeLog(type, tag, text);

        for(int i = 0;i<commonLog.getLogList().size();i++){
            printLog(type,tag,commonLog.getLogList().get(i));
        }
    }
}
