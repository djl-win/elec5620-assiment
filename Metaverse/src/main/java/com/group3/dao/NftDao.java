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
     * Query all nft for this user
     * @param userId user id
     * @return This user nft collection
     */
    ArrayList<Nft> selectAllNftsByUserId(@Param("userid")int userId);

    /**
     * Modify the price and version of NFT, version is 1, then it is sold
     * @param nft nft information
     * @return This user nft collection
     */
    int modifyNftVersionAndPrice(@Param("nft")Nft nft);

    /**
     * Query the information of nft being sold with version 1
     * @param userId User id
     * @return The nft collection being sold of this user
     */
    ArrayList<Nft> selectAllNftOnSellByUserId(int userId);

    /**
     * search nft by nftid
     * @param nftId nft id
     * @return nft
     */
    Nft selectNftByNftId(@Param("nftid") int nftId);

    /**
     * paging nfts
     * @param pageNumber page number
     * @return 4 nfts by page
     */
    ArrayList<Nft> fetchNftByPageNumber(@Param("pageNumber")int pageNumber);

    /**
     * search count in nft table
     * @return  count
     */
    int selectNftCount();

    /**
     * updateNftLikesByNftId
     * @param nft nft
     * @return 1 or 0
     */
    int updateNftLikesByNftId(@Param("nft")Nft nft);

    /**
     * Inquire about 8 nfts in the market
     * @param pagenumber pageNumber
     * @param userId userId
     * @return NFTs
     */
    ArrayList<Nft> selectNftByPageNumber(@Param("pageNumber")int pagenumber, @Param("userId")int userId);

    /**
     * search count in nft table
     * @param userId userId
     * @return  count
     */
    int selectNftOnMarketCount( @Param("userId")int userId);

    /**
     * trade nft
     * @param nft nft info
     * @return affect row number
     */
    int tradeNft(@Param("nft")Nft nft);

    /**
     * select rank info Order ByLike
     * @return rank info
     */
    ArrayList<Nft> selectNftOrderByLike();
}
