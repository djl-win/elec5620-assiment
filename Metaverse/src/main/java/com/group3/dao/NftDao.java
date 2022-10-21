package com.group3.dao;

import com.group3.domain.Nft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

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

    /**
     * 查询此用户所有nft，返回
     * @param userId 用户id
     * @return 此用户nft集合
     */
    ArrayList<Nft> selectAllNftsByUserId(@Param("userid")int userId);

    /**
     * 修改NFT的价格和版本，version为 1，则为出售状态
     * @param nft nft信息
     * @return 此用户nft集合
     */
    int modifyNftVersionAndPrice(@Param("nft")Nft nft);

    /**
     * 查询正在出售的nft信息，version为1
     * @param userId 用户id
     * @return 此用户正在出售的nft集合
     */
    ArrayList<Nft> selectAllNftOnSellByUserId(int userId);
}
