package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.liuhanze.iutil.lang.IString;

public final class ILog {

    private static final String TAG = "liuhanze";

    private static boolean isDebug = true;
    /**
     * logcat 一条日志最大长度.
     */
    private static final int MAX_LOG_LENGTH = 2000;
    /**
     * 日志打印等级，默认只打印向一层函数调用栈，最大可以打印3层
     */
    private static int stackTraceLevel = 1;
    /**
     * 日志打印等级，最大可以打印3层
     */
    private static int MAX_STACK_TRACE_LEVEL = 3;

    private ILog(){

    }

    /**
     * 日志打印等级，默认只打印向一层函数调用栈，最大可以打印3层
     * @param level
     */
    public static void setStackTraceLevel(int level){
        if(level > 3) level = 3;
        if (level <= 0) level = 1;

        stackTraceLevel = level;
    }

    public static void isDebug(boolean debug){
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

    /**
     * log打印
     * @param type 日志等级 Error,Warn,Debug,Info
     * @param tag tag
     * @param text 日志
     */
    private static void log(int type,@NonNull String tag,@NonNull String text){

        if(IString.isEmpty(text)){
            text = "";
        }

        if(isDebug || type == Log.ERROR || Log.WARN == type){

            int endLineLen = 0;
            //打印调用者className，和methodName
            for(int i=0;i<Thread.currentThread().getStackTrace().length;i++){
                StackTraceElement element = Thread.currentThread().getStackTrace()[i];
                if(element.getClassName().equals("com.liuhanze.iutil.log.ILog") && element.getMethodName().equals("log")){

                    for(int j=stackTraceLevel; j>0;j--){
                        //这里为什么+1 因为本函数上一次是本类的调用函数 LogDebug等
                        StackTraceElement stack = Thread.currentThread().getStackTrace()[i+1+j];
                        String stackInfo = IString.concat("-------------"+stack.getClassName(),"/",stack.getMethodName(),"()--------------");
                        if(stackInfo.length() > endLineLen)
                            endLineLen = stackInfo.length();

                        printLog(type,tag,"|"+stackInfo);
                    }


                    printLog(type,tag,"|log length :"+text.length());

                    break;
                }
            }

            int strLength = text.length();
            int start = 0;
            int end = MAX_LOG_LENGTH;
            int subNum = (int) Math.ceil(text.length()*1.0 / MAX_LOG_LENGTH);

            for (int i = 0; i < subNum; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    printLog(type,tag, "|"+(i+1)+"L ("+(end-start)+"c) "+text.substring(start, end));
                    start = end;
                    end = end + MAX_LOG_LENGTH;
                } else {
                    printLog(type,tag, "|"+(i+1)+"L ("+(strLength-start)+"c) "+text.substring(start, strLength));
                    break;
                }
            }

            StringBuilder endLine = new StringBuilder("|");
            for(int i=0;i<endLineLen;i++){
                endLine.append("-");
            }
            printLog(type,tag,endLine.toString());

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
