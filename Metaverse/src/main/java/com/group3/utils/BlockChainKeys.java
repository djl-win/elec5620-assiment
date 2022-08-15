package com.group3.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class BlockChainKeys {
    private static final String ALGORITHM = "RSA";

    //RSA加密明文大小限制
    private static final int MAX_ENCRYPT_SIZE = 117;

    //RSA解密明文大小限制
    private static final int MAX_DECRYPT_SIZE = 128;

    private static final String UTF8 = StandardCharsets.UTF_8.name();

    /**
     *
     * @return 生成的公钥和私钥
     * @throws Exception
     */
    public String[] generatePubAndPriKeys() throws Exception {

        String[] twoKeys = new String[2];

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);

        keyPairGenerator.initialize(1024);

        //通过生成器生成密钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey pub = keyPair.getPublic();

        //转为字节数组
        byte[] pubCode = pub.getEncoded();
        String pubString = Base64.encodeBase64String(pubCode);

        PrivateKey pri = keyPair.getPrivate();

        //转为字节数组
        byte[] priCode = pri.getEncoded();
        String priString = Base64.encodeBase64String(priCode);

        //public key
        twoKeys[0] = pubString;

        //private key
        twoKeys[1] = priString;

        return twoKeys;
    }

    /**
     * 加密
     * @param originalContext 需要加密的内容
     * @param key 进行加密的key
     * @return 加密后的结果,base64
     */
    public String encrypt(String originalContext, Key key) throws Exception, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,key);

        //BASE64加密后的字符串
        byte[] encryptBytes = doCodec(cipher,originalContext.getBytes(UTF8),MAX_ENCRYPT_SIZE);
        return Base64.encodeBase64String(encryptBytes);
    }

    /**
     * 解密
     * @param encryptContext 加密后的内容
     * @param key 进行解密的key
     * @return 解密base64后的结果
     */
    public String decrypt(String encryptContext, Key key) throws Exception, NoSuchAlgorithmException {
        //BASE64解码后的字符串
        byte[] decodeBase64 = Base64.decodeBase64(encryptContext);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decryptBytes = doCodec(cipher,decodeBase64,MAX_DECRYPT_SIZE);
        return new String(decryptBytes);
    }

    /**
     * 执行加密或解密
     * @param cipher 解密还是加密
     * @param bytes 原始数据
     * @param maxSizeBlock 最大长度限制
     * @return 加密或者解密后的字节数组
     */
    public byte[] doCodec(Cipher cipher, byte[] bytes, int maxSizeBlock) throws IllegalBlockSizeException, BadPaddingException, IOException {

        int inputSize = bytes.length;

        //偏移量
        int offset = 0;

        byte[] cache;

        //第几次读取
        int i = 0;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //分段处理传进来的字节数组
        while ((inputSize - offset) > 0){

            if ((inputSize - offset) > maxSizeBlock){
                //maxsize为一次处理的字节长度
                cache = cipher.doFinal(bytes, offset, maxSizeBlock);
            } else {
                cache = cipher.doFinal(bytes, offset, inputSize - offset);
            }

            //把每一次循环的的cache存入输出流
            baos.write(cache,0,cache.length);

            i++;

            offset = i * maxSizeBlock;

        }

        //加解密的结果
        byte[] result = baos.toByteArray();

        baos.close();

        return result;
    }

    /**
     * 获取公钥
     * publicKey 用户账户的公钥【地址值】
     * @return 返回获取的公钥(对通过64加密后的的公钥字符串进行处理，解码base64，再转化为公钥对象)
     */
    public PublicKey getPublicKey(String publicKey) throws Exception {
        //对公钥字符串进行解码
        byte[] decodeBase64 = Base64.decodeBase64(publicKey);
        //rule of public key is X509
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodeBase64);
        //实例化工程keyFactory
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        //获取公钥对象
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 获取私钥
     * @param privateKey 经过base64编码后存储的私钥字符串
     * @return 把编码后的私钥字符串进行处理，转化为私钥对象返回
     */
    public PrivateKey getPrivateKey(String privateKey) throws Exception {

        //对私钥字符串进行解码
        byte[] decodeBase64 = Base64.decodeBase64(privateKey);

        //rule of private key is PKCS8
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodeBase64);

        //实例化工程keyFactory
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        //获取私钥对象
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);

    }


}
