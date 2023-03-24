package com.liuhanze.iutil.file;

import static com.liuhanze.iutil.lang.IString.EMPTY;

import androidx.annotation.Nullable;

import com.liuhanze.iutil.lang.IString;
import com.liuhanze.iutil.log.ILog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class IFile {

    private static final int READ_NORMAL = 0x00;
    private static final int READ_CERTIFICATE_KEY = 0x01;
    /**
     * 换行符
     */
    private static final String LINE_BREAK = "\r\n";
    /**
     * 文件类型
     */
    public static final String[][] MIME_MapTable={

            //{后缀名， MIME类型}

            {".3gp", "video/3gpp"},

            {".apk", "application/vnd.android.package-archive"},

            {".asf", "video/x-ms-asf"},

            {".avi", "video/x-msvideo"},

            {".bin", "application/octet-stream"},

            {".bmp", "image/bmp"},

            {".c", "text/plain"},

            {".class", "application/octet-stream"},

            {".conf", "text/plain"},

            {".cpp", "text/plain"},

            {".doc", "application/msword"},

            {".exe", "application/octet-stream"},

            {".gif", "image/gif"},

            {".gtar", "application/x-gtar"},

            {".gz", "application/x-gzip"},

            {".h", "text/plain"},

            {".htm", "text/html"},

            {".html", "text/html"},

            {".jar", "application/java-archive"},

            {".java", "text/plain"},

            {".jpeg", "image/jpeg"},

            {".jpg", "image/jpeg"},

            {".js", "application/x-javascript"},

            {".log", "text/plain"},

            {".m3u", "audio/x-mpegurl"},

            {".m4a", "audio/mp4a-latm"},

            {".m4b", "audio/mp4a-latm"},

            {".m4p", "audio/mp4a-latm"},

            {".m4u", "video/vnd.mpegurl"},

            {".m4v", "video/x-m4v"},

            {".mov", "video/quicktime"},

            {".mp2", "audio/x-mpeg"},

            {".mp3", "audio/x-mpeg"},

            {".mp4", "video/mp4"},

            {".mpc", "application/vnd.mpohun.certificate"},

            {".mpe", "video/mpeg"},

            {".mpeg", "video/mpeg"},

            {".mpg", "video/mpeg"},

            {".mpg4", "video/mp4"},

            {".mpga", "audio/mpeg"},

            {".msg", "application/vnd.ms-outlook"},

            {".ogg", "audio/ogg"},

            {".pdf", "application/pdf"},

            {".png", "image/png"},

            {".pps", "application/vnd.ms-powerpoint"},

            {".ppt", "application/vnd.ms-powerpoint"},

            {".prop", "text/plain"},

            {".rar", "application/x-rar-compressed"},

            {".rc", "text/plain"},

            {".rmvb", "audio/x-pn-realaudio"},

            {".rtf", "application/rtf"},

            {".sh", "text/plain"},

            {".tar", "application/x-tar"},

            {".tgz", "application/x-compressed"},

            {".txt", "text/plain"},

            {".wav", "audio/x-wav"},

            {".wma", "audio/x-ms-wma"},

            {".wmv", "audio/x-ms-wmv"},

            {".wps", "application/vnd.ms-works"},

            {".xml", "text/plain"},

            {".z", "application/x-compress"},

            {".zip", "application/zip"},

            {"", "*/*"}

    };


    private IFile(){

    }

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

    /**
     * 获取目录下所有文件
     * <p>不递归进子目录</p>
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * 获取目录下所有文件
     * <p>不递归进子目录</p>
     *
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     *
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }


    /**
     * 判断文件目录是否存在
     *
     * @param dirPath 文件目录路径
     * @return
     */
    public static boolean isFolderExist(final String dirPath) {
        return isFolderExist(getFileByPath(dirPath));
    }

    /**
     * 判断文件目录是否存在
     *
     * @param dir
     * @return
     */
    public static boolean isFolderExist(final File dir) {
        return dir != null && dir.exists() && dir.isDirectory();
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    @Nullable
    public static File getFileByPath(final String filePath) {
        return IString.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件夹是否存在 不存在则创建
     * @param file
     * @return
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回 true，是目录则返回 false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(final File file, final String newName) {
        // 文件为空返回 false
        if (file == null) {
            return false;
        }
        // 文件不存在返回 false
        if (!file.exists()) {
            return false;
        }
        // 新的文件名为空返回 false
        if (IString.isEmpty(newName)) {
            return false;
        }
        // 如果文件名没有改变返回 true
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回 false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * 获取文件类型
     * @param file
     * @return
     */
    public static String getMIMEType(File file) {

        String type="*/*";

        String fName = file.getName();

        //获取后缀名前的分隔符"."在fName中的位置。

        int dotIndex = fName.lastIndexOf(".");

        if(dotIndex < 0)

            return type;

        /* 获取文件的后缀名 */

        String fileType = fName.substring(dotIndex,fName.length()).toLowerCase();

        if(fileType == null || "".equals(fileType))

            return type;

        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){

            if(fileType.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
            return type;
        }

        return type;

    }

}
