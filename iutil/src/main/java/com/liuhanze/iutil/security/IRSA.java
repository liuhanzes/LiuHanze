package com.liuhanze.iutil.security;

import com.liuhanze.iutil.file.IFile;
import com.liuhanze.iutil.log.ILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public final class IRSA {

    public static final String RSA = "RSA";
    public static final String RSA_PKCS1_PADDING = "RSA/NONE/PKCS1Padding";
    public static final String RSA_NO_PADDING = "RSA/NONE/NoPadding";
    public static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

    private IRSA(){

    }

    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return
     */
    public static KeyPair generateRSAKeyPair(String algorithm)
    {
        return generateRSAKeyPair(1024,algorithm);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength
     *            密钥长度，范围：512～2048<br>
     *            一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength,String algorithm)
    {
        try
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in
     *            公钥输入流
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in,String algorithm) throws Exception
    {
        try
        {
            return loadPublicKey(IFile.readCertificateKeyInputStream(in),algorithm);
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
    public static PublicKey loadPublicKey(String publicKeyStr,String algorithm) throws Exception
    {
        try
        {
            byte[] buffer = IBase64.decodeCertificateKey(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
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
     * 加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     *            需加密数据的byte数据
     * @param key
     *            加密key
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, Key key, String transformation)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(transformation);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e)
        {
            e.printStackTrace();
            ILog.LogError("IRSA encryptData error");
            return null;
        }
    }

    /**
     * 解密
     *
     * @param encryptedData
     *            经过encryptedData()加密返回的byte数据
     * @param key
     *            解密key
     * @return
     */
    public static byte[] decryptData(byte[] encryptedData, Key key,String algorithm)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encryptedData);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes,String algorithm) throws NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将私钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes,String algorithm) throws NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用参数值还原私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String algorithm,String n, String e,String d,String p,String q,String dp,String dq,String iqmp)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        BigInteger modulus = new BigInteger(n,16);
        BigInteger publicExponent = new BigInteger(e,16);
        BigInteger privateExponent = new BigInteger(d,16);
        BigInteger primeP = new BigInteger(p,16);
        BigInteger primeQ = new BigInteger(q,16);
        BigInteger exponentDp = new BigInteger(dp,16);
        BigInteger exponentDq = new BigInteger(dq,16);
        BigInteger coefficient = new BigInteger(iqmp,16);

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus,publicExponent,privateExponent,primeP,primeQ,exponentDp,exponentDq,coefficient);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 使用N、e值还原公钥
     *
     * @param modulus
     * @param exponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(BigInteger modulus, BigInteger exponent,String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent,String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr,String algorithm) throws Exception
    {
        byte[] buffer = IBase64.decodeCertificateKey(privateKeyStr);
        // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }


    /**
     * 从文件中加载私钥
     *
     * @param
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in,String algorithm) throws Exception
    {
        try
        {
            return loadPrivateKey(readKey(in),algorithm);
        } catch (IOException e)
        {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e)
        {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null)
        {
            if (readLine.charAt(0) == '-')
            {
                continue;
            } else
            {
                sb.append(readLine);
            }
        }
        return sb.toString();
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey)
    {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey)
    {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

    }
}
