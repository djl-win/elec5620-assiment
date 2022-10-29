package com.group3.service;

import com.group3.domain.Nft;
import com.group3.dto.FollowInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface FollowService {

    /**
     * search all info by userid (userdetail user nft)
     * @param userid userid
     * @return all info of follows
     */
    ArrayList<FollowInfo> fetchFollowsByUserId(int userid);

    /**
     * cancel follow by follow id
     * @param followId follow id
     * @return success or not
     */
    boolean cancelFollowByFollowId(int followId);

    /**
     * new follow
     * @param userId userid
     * @param nft nft
     * @return flag
     */
    int newFollow(int userId, Nft nft);

}
