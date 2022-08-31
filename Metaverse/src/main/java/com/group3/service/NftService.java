package com.group3.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface NftService {

    /**
     *
     * @param base64 nft的base64编码数据
     * @param userId
     * @return 成功或失败
     */
    Boolean generateNft(String base64, int userId);
}
