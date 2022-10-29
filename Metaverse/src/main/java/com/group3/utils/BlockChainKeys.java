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

    //RSA encryption plaintext size limit
    private static final int MAX_ENCRYPT_SIZE = 117;

    //RSA decryption plaintext size limit
    private static final int MAX_DECRYPT_SIZE = 128;

    private static final String UTF8 = StandardCharsets.UTF_8.name();

    /**
     *
     * @return Generated public and private keys
     * @throws Exception
     */
    public String[] generatePubAndPriKeys() throws Exception {

        String[] twoKeys = new String[2];

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);

        keyPairGenerator.initialize(512);

        //Generate key by generator
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey pub = keyPair.getPublic();

        //Convert to byte arrays
        byte[] pubCode = pub.getEncoded();
        String pubString = Base64.encodeBase64String(pubCode);

        PrivateKey pri = keyPair.getPrivate();

        //Convert to byte arrays
        byte[] priCode = pri.getEncoded();
        String priString = Base64.encodeBase64String(priCode);

        //public key
        twoKeys[0] = pubString;

        //private key
        twoKeys[1] = priString;

        return twoKeys;
    }

    /**
     * Encryption
     * @param originalContext Content to be encrypted
     * @param key The key for encryption
     * @return The result after encryption, base64
     */
    public String encrypt(String originalContext, Key key) throws Exception, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,key);

        //base64 encrypted string
        byte[] encryptBytes = doCodec(cipher,originalContext.getBytes(UTF8),MAX_ENCRYPT_SIZE);
        return Base64.encodeBase64String(encryptBytes);
    }

    /**
     * 解密
     * @param encryptContext Encrypted content
     * @param key The key for decryption
     * @return The result after decrypting base64
     */
    public String decrypt(String encryptContext, Key key) throws Exception, NoSuchAlgorithmException {
        //BASE64 decoded string
        byte[] decodeBase64 = Base64.decodeBase64(encryptContext);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decryptBytes = doCodec(cipher,decodeBase64,MAX_DECRYPT_SIZE);
        return new String(decryptBytes);
    }

    /**
     * Perform encryption or decryption
     * @param cipher Decryption or encryption
     * @param bytes Raw data
     * @param maxSizeBlock Maximum length limit
     * @return Encrypted or decrypted byte arrays
     */
    public byte[] doCodec(Cipher cipher, byte[] bytes, int maxSizeBlock) throws IllegalBlockSizeException, BadPaddingException, IOException {

        int inputSize = bytes.length;

        //Offset
        int offset = 0;

        byte[] cache;

        //Read counters
        int i = 0;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //Segmentation of incoming byte arrays
        while ((inputSize - offset) > 0){

            if ((inputSize - offset) > maxSizeBlock){
                //maxsize is the length of bytes to be processed at one time
                cache = cipher.doFinal(bytes, offset, maxSizeBlock);
            } else {
                cache = cipher.doFinal(bytes, offset, inputSize - offset);
            }

            //Store the cache of each loop into the output stream
            baos.write(cache,0,cache.length);

            i++;

            offset = i * maxSizeBlock;

        }

        //Results of encryption and decryption
        byte[] result = baos.toByteArray();

        baos.close();

        return result;
    }

    /**
     * Obtain public key
     * publicKey Public key of the user account [address value]
     * @return Return the obtained public key (process the public key string through 64 encryption,
     * decode base64, and then convert it into a public key object)
     */
    public PublicKey getPublicKey(String publicKey) throws Exception {
        //Decode the public key string
        byte[] decodeBase64 = Base64.decodeBase64(publicKey);
        //rule of public key is X509
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodeBase64);
        //Instantiated projects: keyFactory
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        //Get the public key object
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * Obtaining the private key
     * @param privateKey The private key string stored after base64 encoding
     * @return Process the encoded private key string and return it as a private key object
     */
    public PrivateKey getPrivateKey(String privateKey) throws Exception {

        //Decode the private key string
        byte[] decodeBase64 = Base64.decodeBase64(privateKey);

        //rule of private key is PKCS8
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodeBase64);

        //Instantiated projects: keyFactory
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        //Get the public key object
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);

    }


}
