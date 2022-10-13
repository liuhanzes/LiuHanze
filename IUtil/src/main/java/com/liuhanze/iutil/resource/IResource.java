package com.liuhanze.iutil.resource;

import static com.liuhanze.iutil.lang.IString.EMPTY;

import android.content.res.AssetManager;

import com.liuhanze.iutil.IUtil;
import com.liuhanze.iutil.lang.IString;
import com.liuhanze.iutil.log.ILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IResource {
    /**
     * 换行符
     */
    private static final String LINE_BREAK = "\r\n";

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileFromAssets(String fileName) {
        return getFileFromAssets(fileName, true);
    }

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName      文件名
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String getFileFromAssets(String fileName, boolean isNeedAddLine) {
        if (IString.isEmpty(fileName)) {
            return EMPTY;
        }
        return readInputStream(openAssetsFile(fileName), isNeedAddLine);
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     */
    public static InputStream openAssetsFile(String fileName) {
        try {
            return openAssetsFileWithException(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            ILog.LogError(e.toString());
        }
        return null;
    }

    /**
     * 获取AssetManager
     *
     * @return
     */
    public static AssetManager getAssetManager() {
        return IUtil.getContext().getResources().getAssets();
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     * @throws IOException
     */
    public static InputStream openAssetsFileWithException(String fileName) throws IOException {
        return getAssetManager().open(fileName);
    }

    /**
     * 读取输入流
     *
     * @param inputStream   输入流
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String readInputStream(InputStream inputStream, boolean isNeedAddLine) {
        StringBuilder s = new StringBuilder(EMPTY);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            if (isNeedAddLine) {
                while ((line = br.readLine()) != null) {
                    s.append(line).append(LINE_BREAK);
                }
            } else {
                while ((line = br.readLine()) != null) {
                    s.append(line);
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


}
