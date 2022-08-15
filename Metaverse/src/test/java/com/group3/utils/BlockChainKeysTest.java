package com.group3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootTest
public class BlockChainKeysTest {

    @Autowired
    private BlockChainKeys blockChainKeys;

    @Test
    public void testCheckPubAndPriKeys() throws Exception {

        //输入用户公钥
        PublicKey publicKey = blockChainKeys.getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHLTuNpWU3ofzbTn2MPX+aeMJjB+NzmD1mPkpDIkbexX+hszHE8C9r/hBmeO1nfKOwbVKPZlaHbx1k5vTcCiNXE/Lh9U2jpf/atfl34Ko2XWa7aYgl+KMIwQnCvMojG2rPB5Gkq2ncsbYOsljdB+skmdZo+xFAYW7I575ty7j8HQIDAQAB");

        //解密地址
        String after =  blockChainKeys.encrypt("good night!", publicKey);

        PrivateKey privateKey =  blockChainKeys.getPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIctO42lZTeh/NtOfYw9f5p4wmMH43OYPWY+SkMiRt7Ff6GzMcTwL2v+EGZ47Wd8o7BtUo9mVodvHWTm9NwKI1cT8uH1TaOl/9q1+XfgqjZdZrtpiCX4owjBCcK8yiMbas8HkaSradyxtg6yWN0H6ySZ1mj7EUBhbsjnvm3LuPwdAgMBAAECgYAigEn96iP+Ygrekpb6r0udSosaqY3FGCs7qFRd9CawfyoHRGxEfqUr5AmdjenSCv7gbdavoQgewx52fXgrnS7NkNn6pOgI9jAk2ng/uw9+idAXBuLILzlE5B+6OtptCkwEVM7UTcUWKfWkweq9lNAAd2SzFO0vvzKjIoAu2zmx4QJBAL/EpI39Afg1k6Ioqbl2OeViC7s9WLrCWc3GWDgdv+AjAsn4fd4MfyJmqo13VqsXQTu5zDRk4HrDJ0zwxwYu8tUCQQC0dBkGu4lDvjOhlJ/h8kJUYSykqyShgO+jAwoe+seG3nZDFxN6h76yh2kMWvl5ruhxVPD82PY/Xrh3uGc4f7gpAkBuppFomF9lj8yC2cGtEMmxUJSCUpB7Gp9ku5i1sHzBYJJAZdCCqmVFPUYV4JEyoXVzd+6bIMXiL+7iV2APclGNAkBqFdagnp+Ts72mwDk+G63i7T3RkMt4JtS6gI7yp3ulTiM4TdilsCfrzK91FAaWAr2j4svJ2cLqfkT0HPSRwoQBAkA1a2GV0F5H09GIuRXxBLxp2uL8SZdNGjxKgVIC+4rVTdQlfSfDQkA7EKcnMtQZzd5pkEgwFOb0HHwS9t1WsTVK");

        String decrypt = blockChainKeys.decrypt(after, privateKey);

        System.out.println(decrypt);
    }
}
