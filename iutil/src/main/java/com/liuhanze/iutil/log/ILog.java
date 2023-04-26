package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.liuhanze.iutil.lang.IString;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class ILog {

    private static final String TAG = "liuhanze";

    private static boolean isDebug = true;
    /**
     * logcat 一条日志最大长度.
     */
    private static final int MAX_LOG_LENGTH = 500;
    /**
     * 打印装饰线最小长度
     */
    private static final int MIN_LINE_LENGTH = 100;
    /**
     * 日志打印等级，默认只打印向一层函数调用栈，最大可以打印3层
     */
    private static int stackTraceLevel = 1;
    /**
     * 日志打印等级，最大可以打印3层
     */
    private static final int MAX_STACK_TRACE_LEVEL = 3;

    private ILog(){

    }

    /**
     * 日志打印等级，默认只打印向一层函数调用栈，最大可以打印3层
     * @param level
     */
    public static void setStackTraceLevel(int level){
        if(level > MAX_STACK_TRACE_LEVEL) level = MAX_STACK_TRACE_LEVEL;
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
    private static synchronized void log(int type,@NonNull String tag,@NonNull String text){

        if(IString.isEmpty(text)){
            text = "";
        }

        if(isDebug || type == Log.ERROR || Log.WARN == type){

            StringBuilder topLine = new StringBuilder("╔");
            StringBuilder endLine = new StringBuilder("╚");
            StringBuilder line = new StringBuilder("╟");
            List<String> stackList = new ArrayList<>();
            List<String> logList = new ArrayList<>();
            int lineLength = MIN_LINE_LENGTH;

            for(int i=0;i<Thread.currentThread().getStackTrace().length;i++){
                StackTraceElement element = Thread.currentThread().getStackTrace()[i];
                if(element.getClassName().equals("com.liuhanze.iutil.log.ILog") && element.getMethodName().equals("log")){

                    for(int j = stackTraceLevel; j>0;j--){
                        //这里为什么+1 因为本函数上一次是本类的调用函数 LogDebug等
                        StringBuilder spaceChar = new StringBuilder();
                        for(int k=0;k<=MAX_STACK_TRACE_LEVEL-j;k++){
                            spaceChar.append("-");
                        }

                        StackTraceElement stack = Thread.currentThread().getStackTrace()[i+1+j];
                        String stackInfo = IString.concat("║",spaceChar.toString(),stack.getClassName().substring(stack.getClassName().lastIndexOf(".")+1),".",stack.getMethodName()+"("+stack.getFileName()+":"+stack.getLineNumber()+")\n");
                        if(stackInfo.length() > lineLength){
                            lineLength = stackInfo.length();
                        }
                        stackList.add(stackInfo);
                    }

                    break;
                }
            }


            if(text.length() <= MAX_LOG_LENGTH){
                logList.add(text);
                if(text.length() > lineLength){
                    lineLength = text.length();
                }
            }else{
                lineLength = MAX_LOG_LENGTH;
                int strLength = text.length();
                int start = 0;
                int end = MAX_LOG_LENGTH;
                int subNum = (int) Math.ceil(text.length()*1.0 / MAX_LOG_LENGTH);

                for (int i = 0; i < subNum; i++) {
                    //剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        String log = (i+1)+"L ("+(end-start)+"c) "+text.substring(start, end);

                        if(log.length() > lineLength)
                            lineLength = log.length()+1;

                        logList.add(log);
                        start = end;
                        end = end + MAX_LOG_LENGTH;
                    } else {
                        logList.add((i+1)+"L ("+(strLength-start)+"c) "+text.substring(start, strLength));
                        break;
                    }
                }

            }

            for(int i = 0 ; i < lineLength ; i++){
                topLine.append("═");
            }
            topLine.append("╗");

            for(int i = 0 ; i < lineLength ; i++){
                line.append("┄");
            }
            line.append("╢");

            for(int i = 0 ; i < lineLength ; i++){
                endLine.append("═");
            }
            endLine.append("╝");


            //打印顶部 ╔══════════╗ 装饰线
            printLog(type,tag,topLine.toString());

            //打印调用栈信息
            for(int i = 0; i<stackList.size();i++){
                printLog(type,tag,stackList.get(i));
            }

            //打印log和调用栈分割线 ╟┄┄┄┄┄┄┄┄┄╢
            printLog(type,tag,line.toString());

            //打印log
            for(int i = 0 ; i < logList.size() ; i++){
                printLog(type,tag,"║ "+logList.get(i));
            }

            //打印底部 ╚══════════╝ 装饰线
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
