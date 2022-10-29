package com.group3.service.impl;

import com.group3.dao.FollowDao;
import com.group3.dao.NftDao;
import com.group3.dao.UserDao;
import com.group3.dao.UserDetailDao;
import com.group3.domain.Follow;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import com.group3.dto.FollowInfo;
import com.group3.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowDao followDao;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private NftDao nftDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ArrayList<FollowInfo> fetchFollowsByUserId(int userid) {
        ArrayList<FollowInfo> followInfos = new ArrayList<>();

        ArrayList<Follow> allFollowsByUserId = followDao.findAllFollowsByUserId(userid);
        for (Follow follow : allFollowsByUserId) {

            //search nft by nft id
            Nft nft = nftDao.selectNftByNftId(follow.getFollowNftId());

            //search userdetail by userid
            UserDetail userDetail = userDetailDao.selectUserDetailByUserId(nft.getNftUserId());

            //search user by userid
            User user = userDao.selectUserByUserId(nft.getNftUserId());

            //search number followed
            int numberOfNftFollowedByUserId = followDao.findNumberOfNftFollowedByUserId(nft.getNftUserId());

            //encapsulate
            FollowInfo followInfo = new FollowInfo();
            followInfo.setFollow(follow);
            followInfo.setUserDetail(userDetail);
            followInfo.setNft(nft);
            followInfo.setUser(user);
            followInfo.setFollowNumber(numberOfNftFollowedByUserId);
            followInfos.add(followInfo);
        }
        return followInfos;
    }

    @Override
    public boolean cancelFollowByFollowId(int followId) {
        return followDao.deleteFollowByFollowId(followId) == 1;
    }

    @Override
    public int newFollow(int userId, Nft nft) {

        int tempFollow = followDao.findAllFollowsByUserIdAndNftId(userId,nft.getNftId());

        if(tempFollow == 0){
            //new follow
            int temp = followDao.insertFollowByUserIdAndNftId(userId,nft.getNftId());

            if(temp == 1){
                //insert success
                return 2;

            } else  return 3; // insert fail
        } else return 1;

    }

}
