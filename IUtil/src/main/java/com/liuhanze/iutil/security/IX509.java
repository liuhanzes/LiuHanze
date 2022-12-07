package com.liuhanze.iutil.security;

import android.annotation.SuppressLint;

import com.liuhanze.iutil.lang.IString;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * x509证书管理类
 */
public final class IX509 {

    private IX509(){

    }


    /**
     * 根据给定的type获取keystore实例,如果InputStream,password不为空,
     * 则根据InputStream加载keystore 否则获取一个空的keystore
     * @param in
     * @param type
     * Type 	Description
     * jceks 	The proprietary keystore implementation provided by the SunJCE provider.
     * jks 	    The proprietary keystore implementation provided by the SUN provider.
     * dks 	    A domain keystore is a collection of keystores presented as a single logical keystore. It is specified by configuration data whose syntax is described in DomainLoadStoreParameter.
     * pkcs11 	A keystore backed by a PKCS #11 token.
     * pkcs12 	The transfer syntax for personal identity information as defined in PKCS #12: Personal Information Exchange Syntax v1.1.
     *
     * @return
     */
    public static KeyStore getKeyStore(InputStream in,String type,String password) {

        KeyStore keyStore = null;
        try {

            keyStore = KeyStore.getInstance(type == null ? KeyStore.getDefaultType() : type);
            keyStore.load(in == null ? null : in, IString.isEmpty(password) ? null : password.toCharArray());

        } catch (KeyStoreException e) {
            e.printStackTrace();

        }finally {
            return keyStore;
        }
    }

    /**
     * 根据给定type 获取一个空的keyStore
     * @param type
     * @return
     */
    public static KeyStore getEmptyKeyStore(String type){
        return getKeyStore(null,type,null);
    }

    /**
     * 获取一个空的,默认类型keystore
     * @return
     */
    public static KeyStore getDefaultEmptyKeyStore(){
        return getEmptyKeyStore(null);
    }

    /**
     * 根据inputStream 获取证书集合
     * @param in
     * @return
     */
    public static Collection<? extends Certificate> getCertificates(InputStream in){

        Collection<? extends Certificate> certificates = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            certificates = certificateFactory.generateCertificates(in);
        } catch (CertificateException e) {
            e.printStackTrace();
        }finally {
            return certificates;
        }
    }

    /**
     * 根据inputStream 获取一个证书
     * @param in
     * @return
     */
    public static Certificate getCertificate(InputStream in){
        Certificate certificate = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            certificate = certificateFactory.generateCertificate(in);
        } catch (CertificateException e) {
            e.printStackTrace();
        }finally {
            return certificate;
        }
    }

    /**
     * 根据byte[] 获取一个证书
     * @param bytes
     * @return
     */
    public static Certificate getCertificate(byte[] bytes){
        Certificate certificate = null;
        try {
            InputStream cerInputStream = new ByteArrayInputStream(bytes);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            certificate = certificateFactory.generateCertificate(cerInputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }finally {
            return certificate;
        }
    }

    /**
     * 获取TrustManager[]
     * @return
     */
    public static TrustManager[] getTrustManagers(InputStream inputStream,String algorithm){
        return getTrustManagers(getKeyStoreHasCert(inputStream),algorithm);
    }

    /**
     * 获取TrustManager[]
     * @return
     */
    public static TrustManager[] getTrustManagers(String algorithm,InputStream... inputStreams){
        return getTrustManagers(getKeyStoreHasCert(inputStreams),algorithm);
    }

    /**
     * 获取TrustManager[]
     * @return
     */
    public static TrustManager[] getTrustManagers(KeyStore keyStore,String algorithm){
        TrustManagerFactory trustManagerFactory = null;
        TrustManager[] trustManagers = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(algorithm == null ? TrustManagerFactory.getDefaultAlgorithm() : algorithm);
            //用keyStore实例初始化TrustManagerFactory，这样trustManagerFactory就会信任keyStore中的证书
            trustManagerFactory.init(keyStore);
            trustManagers =  trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            return trustManagers;
        }
    }

    /**
     * SSL(Secure Socket Layer) parameters
     */
    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    /**
     * 传入 keyStore 获取SslSocketFactory和TrustManager
     * @param keyStore
     * @return
     */
    public static SSLParams getSslSocketFactory(KeyStore keyStore,boolean checkCert){
        SSLParams sslParams = new SSLParams();
        X509TrustManager x509TrustManager;

        if(checkCert){
            TrustManager[] trustManagers = getTrustManagers(keyStore,null);
            x509TrustManager = new SafeTrustManager(chooseTrustManager(trustManagers));
        }else{
            x509TrustManager = new UnSafeTrustManager();
        }

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            sslParams.trustManager = x509TrustManager;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }finally {
            return sslParams;
        }
    }

    /**
     * 校验证书的SSLSocFactory
     * @param certCollectionInputStream 证书输入流集合
     * @return
     */
    public static SSLParams getSocFactoryCheckCert(InputStream certCollectionInputStream){
        return getSslSocketFactory(getKeyStoreHasCert(certCollectionInputStream),true);
    }

    /**
     * 校验证书的SSLSocFactory
     * @param inputStreams 证书输入流
     * @return
     */
    public static SSLParams getSocFactoryCheckCert(InputStream ... inputStreams){
        return getSslSocketFactory(getKeyStoreHasCert(inputStreams),true);
    }


    /**
     * 添加证书到keyStore
     * @param inputStreams 可变数量的证书 InputStream
     * @return
     */
    public static KeyStore getKeyStoreHasCert(InputStream... inputStreams){
        KeyStore keyStore = getDefaultEmptyKeyStore();
        int index = 0;
        for (InputStream inputStream : inputStreams){
            Certificate certificate = getCertificate(inputStream);
            try {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        return keyStore;
    }


    /**
     * 添加证书到KeyStore
     * @param certCollectionInputStream 证书集合 inputStream
     * @return
     */
    public static KeyStore getKeyStoreHasCert(InputStream certCollectionInputStream){

        KeyStore keyStore = getDefaultEmptyKeyStore();
        Collection<? extends Certificate> certificates = getCertificates(certCollectionInputStream);

        int index = 0;
        for(Certificate certificate: certificates){
            String certificateAlias = Integer.toString(index++);
            try {
                keyStore.setCertificateEntry(certificateAlias, certificate);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        return keyStore;
    }

    /**
     * 不校验证书的SSLSocFactory
     * @return
     */
    public static SSLParams getSocFactoryUnCheckCert(){
        return getSslSocketFactory(null,false);
    }

    public static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    /**
     * 客户端对服务器端证书进行校验
     * 与本地证书比对
     */
    public static class SafeTrustManager implements X509TrustManager{

        /**
         * 内部 x509TrustManager 保存本地证书,或者网络下载证书,用来和服务器证书进行校验
         */
        private X509TrustManager x509TrustManager;

        private SafeTrustManager(X509TrustManager trustManager){
            x509TrustManager = trustManager;
        }

        public void setTrustManager(X509TrustManager trustManager){
            x509TrustManager = trustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                if(x509TrustManager != null)
                    x509TrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                throw new CertificateException(ce);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 客户端不对证书做任何检查;
     * 客户端不对证书做任何验证的做法有很大的安全漏洞。
     */
    public static class UnSafeTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

}
