package com.liuhanze.iutil.security;

import android.util.Base64;

import com.liuhanze.iutil.lang.IByte;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ISM4 {

    public static final String SM4_NO_PADDING = "SM4/ECB/NoPadding";


    private ISM4(){

    }

    /**
     * SM4 转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式 ECB、加密块链模式 CBC、加密反馈模式 CFB、输出反馈模式 OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    private static final String AES_Algorithm = "SM4";

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * SM4 加密后转为 Base64 编码
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return Base64 密文
     */
    public static byte[] encrypt2Base64(final byte[] data,
                                        final byte[] key,
                                        final String transformation,
                                        final byte[] iv) {
        return base64Encode(encrypt(data, key, transformation, iv));
    }

    /**
     * SM4 加密后转为 16 进制
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 16 进制密文
     */
    public static String encrypt2HexString(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return IByte.bytes2HexString(encrypt(data, key, transformation, iv));
    }

    /**
     * SM4 加密
     *
     * @param data           明文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 密文
     */
    public static byte[] encrypt(final byte[] data,
                                 final byte[] key,
                                 final String transformation,
                                 final byte[] iv) {
        return desTemplate(data, key, AES_Algorithm, transformation, iv, true);
    }

    /**
     * SM4 解密 Base64 编码密文
     *
     * @param data           Base64 编码密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decrypt2Base64(final byte[] data,
                                        final byte[] key,
                                        final String transformation,
                                        final byte[] iv) {
        return decrypt(base64Decode(data), key, transformation, iv);
    }

    /**
     * SM4 解密 16 进制密文
     *
     * @param data           16 进制密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decrypt2HexString(final String data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return decrypt(IByte.hexStringToBytes(data), key, transformation, iv);
    }

    /**
     * SM4 解密
     *
     * @param data           密文
     * @param key            16、24、32 字节秘钥
     * @param transformation 转变
     * @param iv             初始化向量
     * @return 明文
     */
    public static byte[] decrypt(final byte[] data,
                                 final byte[] key,
                                 final String transformation,
                                 final byte[] iv) {
        return desTemplate(data, key, AES_Algorithm, transformation, iv, false);
    }

    /**
     * SM4 加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation  填充方式 例如：SM4/ECB/NoPadding
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
            Cipher cipher = Cipher.getInstance(transformation,new BouncyCastleProvider());
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
