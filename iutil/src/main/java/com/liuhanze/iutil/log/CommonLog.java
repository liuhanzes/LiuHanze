package com.liuhanze.iutil.log;

import androidx.annotation.NonNull;
import com.liuhanze.iutil.lang.IString;

class CommonLog extends LogBase {

    private CommonLog(){

    }


    public static CommonLog getInstance(){
        return InnerHolder.instance;
    }

    @Override
    protected void makeLog(int type, @NonNull String tag, @NonNull String text) {

        getLogList().clear();
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
                log = (i+1)+"L ("+(end-start)+"c) "+text.substring(start, end);
                getLogList().add(log);
                start = end;
                end = end + MAX_LOG_LENGTH;
            } else {
                log = (i+1)+"L ("+(strLength-start)+"c) "+text.substring(start, strLength);
                getLogList().add(log);
            }

            if(log.length() > lineLength)
                lineLength = log.length()+1;
        }

    }


    private static class InnerHolder{
        public static CommonLog instance = new CommonLog();
    }


}
