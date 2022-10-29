package com.group3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

@SpringBootTest
public class BlockChainKeysTest {

    @Autowired
    private BlockChainKeys blockChainKeys;

    @Test
    public void testGenerateKeys() throws Exception {
        String[] strings = blockChainKeys.generatePubAndPriKeys();
        for (String string : strings) {
            System.out.println(string);
        }

    }

    @Test
    public void testCheckPubAndPriKeys() throws Exception {


        PublicKey publicKey = blockChainKeys.getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALZwhE4XxCV/KiD+OYSB0rONmYSZ7JF+gICJFhIsm+vYCnzEssBeW5Cw61xlxCVNOcu3/esToWUYT6sUBC5oDhMCAwEAAQ==");


        String after =  blockChainKeys.encrypt("good night!", publicKey);

        PrivateKey privateKey =  blockChainKeys.getPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtnCEThfEJX8qIP45hIHSs42ZhJnskX6AgIkWEiyb69gKfMSywF5bkLDrXGXEJU05y7f96xOhZRhPqxQELmgOEwIDAQABAkBERwHM9CoNWnUa2eoOxENlvVW6AZ0+qlhPQn7HGHjWH2bi2dBvZ8SgToWrFLd6LlZ60pBnHh/xg0+/L8oGN52hAiEA7t5JONH3vgwBCyv7UigaA2AkD3b5ou+ujGwtozAv+YMCIQDDhi0z4g/GcWBKTPf+FPqCYew4P/NNNpOtR7FwEzvEMQIhAJL/cpooBMkMxBum7lvp19BZ+vcVZZ0S2R0Lea5iejOXAiADw9uu+hVvrtM52w2TePfx9szDsIeQ3xIs7gp+rAAkoQIgSjOLfr7A31cJq5mJ1JvB5M0Pc9GVIbdPQGPkZZw4YAg=");
        String decrypt = blockChainKeys.decrypt(after, privateKey);

        System.out.println(decrypt);
    }
}
