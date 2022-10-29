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
     * @param base64 nft的base64编码数据
     * @param userId
     * @return 成功或失败
     */
    Boolean generateNft(String base64, int userId);


    /**
     * 查询此用户所有nft，返回
     * @param userId 用户id
     * @return 此用户nft集合
     */
    ArrayList<Nft> selectAll(int userId);

    /**
     * 出售nft，version设置为1，价格设置为想要的。
     * @param nft 要修改的信息
     */
    Boolean pushNft(Nft nft);

    /**
     * 查询正在出售的nft信息
     * @param userId 用户id
     * @return 返回前端的消息集合
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
