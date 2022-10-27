package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

public final class ILog {

    private static final String TAG = "liuhanze";

    private static boolean isDebug = true;
    /**
     * logcat 一条日志最大长度.
     */
    private static final int MAX_LOG_LENGTH = 2000;

    private ILog(){

    }

    public static void debug(boolean debug){
        isDebug = debug;
    }

    public static void LogDebug(@NonNull String Tag, @NonNull String text){
        log(Log.DEBUG,Tag,text);
    }

    public static void LogDebug(@NonNull String text){
        log(Log.DEBUG,TAG,text);
    }

    public static void LogError(@NonNull String Tag, @NonNull String text){
        log(Log.ERROR,Tag,text);
    }

    public static void LogError(@NonNull String text){
        log(Log.ERROR,TAG,text);
    }

    public static void LogVerbose(@NonNull String Tag, @NonNull String text){
        log(Log.VERBOSE,Tag,text);
    }

    public static void LogVerbose(@NonNull String text){
        log(Log.VERBOSE,TAG,text);
    }

    public static void LogInfo(@NonNull String Tag, @NonNull String text){
        log(Log.INFO,Tag,text);
    }

    public static void LogInfo(@NonNull String text){
        log(Log.INFO,TAG,text);
    }

    public static void LogWarn(@NonNull String Tag, @NonNull String text){
        log(Log.WARN,Tag,text);
    }

    public static void LogWarn(@NonNull String text){
        log(Log.WARN,TAG,text);
    }

    private static void log(int type,@NonNull String tag,@NonNull String text){
        if(isDebug){
            int strLength = text.length();
            int start = 0;
            int end = MAX_LOG_LENGTH;
            int subNum = text.length() / MAX_LOG_LENGTH;

            if(subNum > 0){
                for (int i = 0; i < subNum; i++) {
                    //剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        printLog(type,tag, text.substring(start, end));
                        start = end;
                        end = end + MAX_LOG_LENGTH;
                    } else {
                        printLog(type,tag, text.substring(start, strLength));
                        break;
                    }
                }
            }else{
                printLog(type,tag,text);
            }



        }
    }

    private static void printLog(int type, @NonNull String tag, @NonNull String text){
        switch (type){
            case Log.VERBOSE:
                Log.v(tag,text);
                break;
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
