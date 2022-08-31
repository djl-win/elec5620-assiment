package com.group3.dao;

import com.group3.domain.Nft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NftDao {

    /**
     * test connection
     * @return
     */
    Nft selectTest();

    /**
     * insert a new nft to database
     * @param nft Encapsulated data
     * @return infect rows
     */
    int insertNft(@Param("nft") Nft nft);
}
