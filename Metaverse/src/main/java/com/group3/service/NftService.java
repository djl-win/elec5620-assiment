package com.group3.service;

import com.group3.domain.Nft;
import com.group3.dto.FollowInfo;
import com.group3.dto.OnSellMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface NftService {

    /**
     *
     * @param base64 Base64 encoded data for nft
     * @param userId
     * @return Success or failure
     */
    Boolean generateNft(String base64, int userId);


    /**
     * Query all nft for this user
     * @param userId User id
     * @return This user nft collection
     */
    ArrayList<Nft> selectAll(int userId);

    /**
     * Sell nft, version set to 1, price set to wanted.
     * @param nft Information to be modified
     */
    Boolean pushNft(Nft nft);

    /**
     * Check the information of nft being sold
     * @param userId User id
     * @return Returns a collection of messages from the front-end
     */
    ArrayList<OnSellMessage> selectAllNftOnSell(int userId);


    /**
     * paging nft
     * @param pageNumber page number
     * @return nfts
     */
    ArrayList<Nft> selectNftsByPages(int pageNumber);

    /**
     * search nfts count by number
     * @return nfts count
     */
    int selectNftsPagesCount();

    /**
     * update nft like
     * @param nft nft
     * @return true or false
     */
    boolean updateNftLikes(Nft nft);

    /**
     * paging nft in market
     * @param pageNumber page number
     * @param userId userId
     * @return nfts
     */
    ArrayList<FollowInfo> selectNftsByPages(int pageNumber, int userId);

    /**
     * select Nfts On Market Pages Count
     * @param userId userid
     * @return page number
     */
    int selectNftsOnMarketPagesCount(int userId);

    /**
     * search rank info order by likes
     * @return rank info
     */
    ArrayList<Nft> getNftRank();
}
