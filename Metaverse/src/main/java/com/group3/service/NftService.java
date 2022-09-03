package com.group3.service;

import com.group3.domain.Nft;
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
}
