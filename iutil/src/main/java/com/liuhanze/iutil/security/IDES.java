package com.liuhanze.iutil.security;

import android.util.Base64;

import com.liuhanze.iutil.lang.IByte;
import com.liuhanze.iutil.lang.IString;
import com.liuhanze.iutil.log.ILog;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class IDES {

    private IDES(){

    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * DES 转变
     * <p>法算法名称/加密模式/填充方式</p> DES
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>transformation 填充方式有： NoPadding、ZerosPadding、PKCS5Padding</p>
     *
     * 例如：DES/ECB/NoPadding
     */
    private static final String DES_Algorithm = "DES";

    /**
     * 随机产生单倍长des密钥
     * @return
     */
    public static String getRandomDesKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(DES_Algorithm);
            keyGenerator.init(64);
            SecretKey secretKey = keyGenerator.generateKey();
            DESKeySpec deSedeKeySpec = new DESKeySpec(secretKey.getEncoded());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_Algorithm);
            Key key =  secretKeyFactory.generateSecret(deSedeKeySpec);
            String keyData = IByte.bytes2HexString(key.getEncoded());
            return  keyData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (InvalidKeySpecException e) {
            return null;
        }
    }

    /**
     * 随机产生双倍长des密钥
     * @return
     */
    public static String getRandomDoubleDesKey(){

        String des1_rand = getRandomDesKey();
        String des2_rand = getRandomDesKey();
        return IString.concat(des1_rand,des2_rand);
    }

    /**
     * 随机产生三倍长des密钥
     * @return
     */
    public static String getRandomTDesKey(){

        String des1_rand = getRandomDesKey();
        String des2_rand = getRandomDesKey();
        String des3_rand = getRandomDesKey();
        return IString.concat(des1_rand,des2_rand,des3_rand);
    }

    /**
     * DES 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation  填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encryptDES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation 填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encryptDES2HexString(final byte[] data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv) {
        return IByte.bytes2HexString(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密
     *
     * @param data           明文
     * @param key            8 字节秘钥
     * @param transformation 填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, DES_Algorithm, transformation, iv, true);
    }

    /**
     * DES 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            8 字节秘钥
     * @param transformation 填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptBase64DES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv) {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * DES 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            8 字节秘钥
     * @param transformation 填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptHexStringDES(final String data,
                                             final byte[] key,
                                             final String transformation,
                                             final byte[] iv) {
        return decryptDES(IByte.hexStringToByteArray(data), key, transformation, iv);
    }

    /**
     * DES 解密
     *
     * @param data           密文
     * @param key            8 字节秘钥
     * @param transformation 填充方式 例如：DES/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return desTemplate(data, key, DES_Algorithm, transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES 加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 3DES 转变
     * <p>法算法名称/加密模式/填充方式</p> DESede
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     *
     * 例：DESede/ECB/NoPadding
     */
    private static final String TripleDES_Algorithm = "DESede";


    /**
     * 3DES 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            24 字节秘钥
     * @param transformation 填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encrypt3DES2Base64(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv) {
        return base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            24 字节秘钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encrypt3DES2HexString(final byte[] data,
                                               final byte[] key,
                                               final String transformation,
                                               final byte[] iv) {
        return IByte.bytes2HexString(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密
     *
     * @param data           明文
     * @param key            16字节/24字节 密钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {
        byte[] mKey = null;


        if(key != null && key.length == 16){
            mKey = new byte[24];
           System.arraycopy(key,0,mKey,0,8);
           System.arraycopy(key,8,mKey,8,8);
           System.arraycopy(key,0,mKey,16,8);

        }else if(key != null && key.length == 24){
            mKey = new byte[24];
            System.arraycopy(key,0,mKey,0,8);
            System.arraycopy(key,8,mKey,8,8);
            System.arraycopy(key,16,mKey,16,8);
        }

        return desTemplate(data, mKey, TripleDES_Algorithm, transformation, iv, true);
    }

    /**
     * 3DES 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            24 字节秘钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptBase64_3DES(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv) {
        return decrypt3DES(base64Decode(data), key, transformation, iv);
    }

    /**
     * 3DES 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            24 字节秘钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decryptHexString3DES(final String data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv) {
        return decrypt3DES(IByte.hexStringToByteArray(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     *
     * @param data           密文
     * @param key            16字节/24字节 密钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static String decrypt3DES2HexString(final byte[] data,
                                               final byte[] key,
                                               final String transformation,
                                               final byte[] iv) {
        return IByte.bytes2HexString(decrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 解密
     *
     * @param data           密文
     * @param key            16字节/24字节 密钥
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {

        byte[] mKey = null;
        if(key != null && key.length == 16){
            mKey = new byte[24];
            System.arraycopy(key,0,mKey,0,8);
            System.arraycopy(key,8,mKey,8,8);
            System.arraycopy(key,0,mKey,16,8);

        }else if(key != null && key.length == 24){
            mKey = new byte[24];
            System.arraycopy(key,0,mKey,0,8);
            System.arraycopy(key,8,mKey,8,8);
            System.arraycopy(key,16,mKey,16,8);
        }

        return desTemplate(data, mKey, TripleDES_Algorithm, transformation, iv, false);
    }

    /**
     * DES 加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation  填充方式 例如：DESede/ECB/NoPadding
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于 DES，3DES，AES
     */
    private static byte[] desTemplate(final byte[] data,
                                      final byte[] key,
                                      final String algorithm,
                                      final String transformation,
                                      final byte[] iv,
                                      final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }


}
