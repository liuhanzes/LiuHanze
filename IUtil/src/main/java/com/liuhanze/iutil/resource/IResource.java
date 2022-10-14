package com.liuhanze.iutil.resource;

import static com.liuhanze.iutil.lang.IString.EMPTY;

import android.content.res.AssetManager;

import com.liuhanze.iutil.IUtil;
import com.liuhanze.iutil.file.IFile;
import com.liuhanze.iutil.lang.IString;
import com.liuhanze.iutil.log.ILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IResource {

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName      文件名
     * @return
     */
    public static String getFileFromAssets(String fileName) {
        if (IString.isEmpty(fileName)) {
            return EMPTY;
        }
        return IFile.readInputStream(openAssetsFile(fileName));
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




}
