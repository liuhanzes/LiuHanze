package com.liuhanze.iutil.log;

import androidx.annotation.NonNull;

abstract class LogDecorator extends LogBase {

    protected LogBase commonLog;

    public void setLog(LogBase log){
        commonLog = log;
    }


    @Override
    protected void makeLog(int type, @NonNull String tag, @NonNull String text) {
        commonLog.makeLog(type,tag,text);
    }
}
