package com.liuhanze.iutil.security;

import com.liuhanze.iutil.lang.IByte;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class IMD5 {

    private static final String MD5 = "MD5";

    private IMD5(){

    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] enCodeToByte(final byte[] data) {
        return hashTemplate(data, MD5);
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String enCodeToHexString(final byte[] data) {
        return IByte.bytes2HexString(enCodeToByte(data));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @return 16 进制密文
     */
    public static String encryptToHexString(final String data) {
        return enCodeToHexString(data.getBytes());
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @return 16 进制密文
     */
    public static String encryptToHexString(final byte[] data) {
        return IByte.bytes2HexString(enCodeToByte(data));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16 进制加盐密文
     */
    public static String encryptToHexString(final String data, final String salt) {
        return IByte.bytes2HexString(enCodeToByte((data + salt).getBytes()));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 16 进制加盐密文
     */
    public static String encodeToHexString(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) {
            return null;
        }
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return IByte.bytes2HexString(enCodeToByte(dataSalt));
    }

    /**
     * MD5 加密文件
     *
     * @param filePath 文件路径
     * @return 文件的 16 进制密文
     */
    public static String encodeFileToHexString(final String filePath) {
        return IByte.bytes2HexString(encodeFile(new File(filePath),MD5));
    }


    /**
     * MD5 加密文件
     *
     * @param file 文件
     * @return 文件的 16 进制密文
     */
    public static String encodeFileToHexString(final File file) {
        return IByte.bytes2HexString(encodeFile(file,MD5));
    }

    /**
     * MD5 加密文件
     *
     * @param filePath 文件路径
     * @return 文件的 MD5 校验码
     */
    public static byte[] encodeFileToByte(final String filePath) {
        return encodeFile(new File(filePath),MD5);
    }

    /**
     * MD5 加密文件
     *
     * @param file 文件
     * @return 文件的 MD5 校验码
     */
    public static byte[] encodeFileToByte(final File file) {
        return encodeFile(file,MD5);
    }


    /**
     * MD5 加密文件
     *
     * @param file 文件
     * @return 文件的 MD5 校验码
     */
    private static byte[] encodeFile(final File file,String mode) {
        if (file == null) {
            return null;
        }

        try(FileInputStream fis = new FileInputStream(file);
            DigestInputStream digestInputStream = new DigestInputStream(fis, MessageDigest.getInstance(mode))) {

            byte[] buffer = new byte[256 * 1024];  //一次读入 256KB
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) {
                    break;
                }
            }
            MessageDigest md  = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * hash 加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
