package com.group3.dao;

import com.group3.domain.Nft;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NftDao {

    /**
     * test connection
     * @return
     */
    Nft selectTest();
}
