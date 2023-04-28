package com.liuhanze.iutil.log;

import androidx.annotation.NonNull;

import com.liuhanze.iutil.lang.IString;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class BeautifulLineLog extends LogDecorator {

    private volatile static BeautifulLineLog instance = null;
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

    private BeautifulLineLog(){}

    public static BeautifulLineLog getInstance(){
        if(instance == null){
            synchronized (BeautifulLineLog.class){
                if(instance == null){
                    instance = new BeautifulLineLog();
                }
            }
        }
        return instance;
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

    @Override
    protected void makeLog(int type, @NonNull String tag, @NonNull String text) {

        StringBuilder topLine = new StringBuilder("╔");
        StringBuilder endLine = new StringBuilder("╚");
        StringBuilder line = new StringBuilder("╟");
        String dotLine = new String("┄".getBytes(), StandardCharsets.UTF_8);
        String fullLine = new String("═".getBytes(),StandardCharsets.UTF_8);
        String normalFullLine = "════════════════════════════════════════════════════════════════════════════════════════════════════";
        String normalDotLine = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
        String verLine = "║ ";
        List<String> stackList = new ArrayList<>();
        lineLength = MIN_LINE_LENGTH;

        for(int j = stackTraceLevel; j>0;j--){

            StackTraceElement stack = Thread.currentThread().getStackTrace()[4+j];

            StringBuilder spaceChar = new StringBuilder();
            for(int k=0;k<=MAX_STACK_TRACE_LEVEL-j;k++){
                spaceChar.append("-");
            }

            String stackInfo = IString.concat("║",spaceChar.toString(),stack.getClassName().substring(stack.getClassName().lastIndexOf(".")+1),".",stack.getMethodName()+"("+stack.getFileName()+":"+stack.getLineNumber()+")\n");
            if(stackInfo.length() > lineLength){
                lineLength = stackInfo.length();
            }
            stackList.add(stackInfo);
        }

        text = text.replace("\n","\n║ ");

        commonLog.lineLength = lineLength;
        super.makeLog(type, tag, text);
        lineLength = commonLog.lineLength;
        setLogList(commonLog.getLogList());

        if(lineLength == MIN_LINE_LENGTH){
            topLine.append(normalFullLine);
            line.append(normalDotLine);
            endLine.append(normalFullLine);
        }else{
            topLine.append(normalFullLine);
            line.append(normalDotLine);
            endLine.append(normalFullLine);

            for(int i = 0 ; i < lineLength-MIN_LINE_LENGTH ; i++){
                topLine.append(fullLine);
                line.append(dotLine);
                endLine.append(fullLine);
            }
        }

        topLine.append("╗");
        line.append("╢");
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
        for(int i = 0 ; i < getLogList().size() ; i++){
            printLog(type,tag,verLine+getLogList().get(i));
        }

        //打印底部 ╚══════════╝ 装饰线
        printLog(type,tag,endLine.toString());

    }
}
