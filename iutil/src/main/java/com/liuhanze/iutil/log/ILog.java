package com.liuhanze.iutil.log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.liuhanze.iutil.lang.IString;

public class ILog extends IPrint{

    /**
     * 打印 带行号和统计单行字符数量
     */
    public static final int NORMAL = 0;
    /**
     * 打印 带行号和统计单行字符数量 并且有装饰线
     * 默认打印形式
     */
    public static final int BEAUTIFUL_LINE = 1;
    /**
     * 直接原样打印
     */
    public static final int FAST = 2;
    /**
     * 打印样式
     */
    private static int STYLE = BEAUTIFUL_LINE;
    /**
     * logcat 一条日志最大长度.
     */
    protected static final int MAX_LOG_LENGTH = 500;

    private static final String TAG = "liuhanze";
    private static boolean isDebug = true;

    private ILog(){

    }

    /**
     * 日志打印等级，默认只打印向一层函数调用栈，最大可以打印3层
     * 只在 BEAUTIFUL_LINE 模式有效
     * @param level
     */
    public static void setStackTraceLevel(int level){
        BeautifulLineLog.setStackTraceLevel(level);
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

    public static void setStyle(int style){
        STYLE = style;
    }

    /**
     * log打印
     * @param type 日志等级 Error,Warn,Debug,Info
     * @param tag tag
     * @param text 日志
     */
    private static synchronized void log(int type,@NonNull String tag,@NonNull String text){

        if(isDebug || type == Log.ERROR || Log.WARN == type){

            switch (STYLE){
                case NORMAL:{
                    LogBase commonLog = CommonLog.getInstance();
                    LogDecorator normalLog = NormalLog.getInstance();
                    normalLog.setLog(commonLog);
                    normalLog.makeLog(type,tag,text);

                }break;
                case BEAUTIFUL_LINE:{
                    LogBase commonLog = CommonLog.getInstance();
                    LogDecorator beautifulLineLog = BeautifulLineLog.getInstance();
                    beautifulLineLog.setLog(commonLog);
                    beautifulLineLog.makeLog(type,tag,text);
                }break;
                case FAST:{
                    fastLog(type,tag,text);
                }break;
            }
        }

    }


    private static void fastLog(int type, @NonNull String tag, @NonNull String text) {

        if(IString.isEmpty(text)){
            text = "";
        }

        int strLength = text.length();
        int start = 0;
        int end = MAX_LOG_LENGTH;
        int subNum = (int) Math.ceil(text.length()*1.0 / MAX_LOG_LENGTH);

        for (int i = 0; i < subNum; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            String log;
            if (strLength > end) {
                log = text.substring(start, end);
                start = end;
                end = end + MAX_LOG_LENGTH;
            } else {
                log = text.substring(start, strLength);
            }

            printLog(type,tag,log);
        }

    }
}
