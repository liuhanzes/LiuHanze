package com.liuhanze.iutil.file;

import static com.liuhanze.iutil.lang.IString.EMPTY;

import com.liuhanze.iutil.log.ILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IFile {

    private static final int READ_NORMAL = 0x00;
    private static final int READ_CERTIFICATE_KEY = 0x01;
    /**
     * 换行符
     */
    private static final String LINE_BREAK = "\r\n";

    /**
     * 读取输入流
     *
     * @param inputStream   输入流
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    private static String readInputStream(InputStream inputStream, boolean isNeedAddLine,int readType) {
        StringBuilder s = new StringBuilder(EMPTY);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {

                switch (readType){
                    case READ_NORMAL:{
                        if(isNeedAddLine){
                            s.append(line).append(LINE_BREAK);
                        }else{
                            s.append(line);
                        }
                    }break;
                    case READ_CERTIFICATE_KEY:{
                        if (line.charAt(0) == '-')
                            continue;
                        else
                            s.append(line);
                    }break;
                    default:
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            ILog.LogError(e.toString());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
                ILog.LogError(e.toString());
            }
        }
        return s.toString();
    }


    /**
     * 读取输入流转字符串 默认换行
     * @param inputStream
     * @return
     */
    public static String readInputStream(InputStream inputStream){
        return readInputStream(inputStream,true,READ_NORMAL);
    }

    /**
     * 读取输入流转字符串 默认换行
     * @param inputStream
     * @return
     */
    public static String readCertificateKeyInputStream(InputStream inputStream){
        return readInputStream(inputStream,false,READ_CERTIFICATE_KEY);
    }

    /**
     * 读取输入流转字符串
     * @param inputStream
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String readInputStream(InputStream inputStream,boolean isNeedAddLine){
        return readInputStream(inputStream,true,READ_NORMAL);
    }

}
