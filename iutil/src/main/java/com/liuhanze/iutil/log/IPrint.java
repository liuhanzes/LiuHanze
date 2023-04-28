package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

public class IPrint {


    public static void printLog(int type, @NonNull String tag, @NonNull String text){
        switch (type){
            case Log.DEBUG:
                Log.d(tag,text);
                break;
            case Log.INFO:
                Log.i(tag,text);
                break;
            case Log.WARN:
                Log.w(tag,text);
                break;
            case Log.ERROR:
                Log.e(tag,text);
                break;
            default:
                Log.v(tag, text);
                break;
        }

    }
}
