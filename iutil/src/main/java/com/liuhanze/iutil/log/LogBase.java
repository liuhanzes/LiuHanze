package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

abstract class LogBase extends IPrint{
    /**
     * logcat 一条日志最大长度.
     */
    protected static final int MAX_LOG_LENGTH = 500;

    private List<String> logList = new ArrayList<>();
    /**
     * 一行日志 真实长度
     */
    protected int lineLength = 0;


    public List<String> getLogList() {
        return logList;
    }

    public void setLogList(List<String> logList) {
        this.logList = logList;
    }

    protected abstract void makeLog(int type, @NonNull String tag, @NonNull String text);


}
