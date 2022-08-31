package com.group3.dao;

import com.group3.domain.Nft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NftDaoTest {

    @Autowired
    private NftDao nftDao;

    @Test
    public void testSelectTest(){
        Nft nft = nftDao.selectTest();
        System.out.println(nft.getNftId());
        System.out.println(nft.getNftSignature());
        System.out.println(nft.getNftUrl());
        System.out.println(nft.getNftPrice());
        System.out.println(nft.getNftDescription());
        System.out.println(nft.getNftLikes());
        System.out.println(nft.getNftDeleted());
        System.out.println(nft.getNftVersion());
        System.out.println(nft.getNftUserId());
    }

    @Test
    public void testInsertNft(){
        Nft nft = new Nft();
        nft.setNftSignature("signature");
        nft.setNftUrl("nftImageUrl");
        nft.setNftPrice(0);
        nft.setNftDescription("not yet config");
        nft.setNftLikes(0);
        nft.setNftUserId(1);

        System.out.println(nftDao.insertNft(nft));
    }
}
