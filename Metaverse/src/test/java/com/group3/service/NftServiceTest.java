package com.group3.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NftServiceTest {

    @Autowired
    private NftService nftService;

    @Test
    public void testGenerateNft(){
        System.out.println(nftService.generateNft("dsa",1));
    }
}
