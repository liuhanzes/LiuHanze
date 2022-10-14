package com.liuhanze.iutil.security;

import com.liuhanze.iutil.file.IFile;
import com.liuhanze.iutil.log.ILog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public final class IRSA {

    private static String RSA = "RSA";
    /**
     * 从文件中输入流中加载公钥
     *
     * @param in
     *            公钥输入流
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception
    {
        try
        {
            return loadPublicKey(IFile.readCertificateKeyInputStream(in));
        } catch (IOException e)
        {
            throw new Exception("func loadPublicKey(InputStream in) public key read inputStream failed");
        } catch (NullPointerException e)
        {
            throw new Exception("func loadPublicKey(InputStream in) public key read inputStream is can't be null");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = IBase64.decodeCertificateKey(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e)
        {
            throw new Exception("func loadPublicKey(String publicKeyStr) NoSuchAlgorithmException ");
        } catch (InvalidKeySpecException e)
        {
            throw new Exception("func loadPublicKey(String publicKeyStr) InvalidKeySpecException");
        } catch (NullPointerException e)
        {
            throw new Exception("func loadPublicKey(String publicKeyStr) NullPointerException");
        }
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     *            需加密数据的byte数据
     * @param publicKey
     *            公钥
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey,String transformation)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(transformation);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e)
        {
            e.printStackTrace();
            ILog.LogError("IRSA encryptData error");
            return null;
        }
    }
}
