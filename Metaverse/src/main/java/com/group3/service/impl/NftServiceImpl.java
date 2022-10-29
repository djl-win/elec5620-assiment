package com.group3.service.impl;

import com.group3.dao.NftDao;
import com.group3.dao.UserDao;
import com.group3.dao.UserDetailDao;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import com.group3.dto.FollowInfo;
import com.group3.dto.OnSellMessage;
import com.group3.service.NftService;
import com.group3.utils.Base64ToFile;
import com.group3.utils.CreateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NftServiceImpl implements NftService {

    @Autowired
    private NftDao nftDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private Base64ToFile base64ToFile;

    //generate uuid util
    @Autowired
    private CreateUuid createUuid;

    @Override
    public Boolean generateNft(String base64, int userId) {

        //Create a new uuid as nft's unique identifier number
        String signature = createUuid.newUuid();

        //Directory for file generation
        String nftImageUrl = "D:/front-end/hkzf/src/assets/nftWorks/" + signature + ".png";

        //1. convert base64 to image and save it locally, and return the address value
        base64ToFile.saveBase64(base64,nftImageUrl);

        //2. Transfer the address value and other information to the database
        Nft nft = new Nft();
        nft.setNftSignature(signature);
        nft.setNftUrl(signature + ".png");
        nft.setNftPrice(0);

        //3.config description
        nft.setNftDescription("creatx nft work");
        nft.setNftLikes(0);
        nft.setNftUserId(userId);
        return nftDao.insertNft(nft) == 1;
    }

    @Override
    public ArrayList<Nft> selectAll(int userId) {

        return nftDao.selectAllNftsByUserId(userId);
    }

    @Override
    public Boolean pushNft(Nft nft) {
        int flag = nftDao.modifyNftVersionAndPrice(nft);
        return flag == 1;
    }

    @Override
    public ArrayList<OnSellMessage> selectAllNftOnSell(int userId) {
        ArrayList<Nft> nfts = nftDao.selectAllNftOnSellByUserId(userId);
        ArrayList<OnSellMessage> onSellMessages = new ArrayList<>();
        for (Nft nft : nfts) {
            OnSellMessage onSellMessage = new OnSellMessage();
            onSellMessage.setImage("https://cutshort-data.s3.amazonaws.com/cloudfront/public/companies/5809d1d8af3059ed5b346ed1/logo-1615367026425-logo-v6.png");
            onSellMessage.setMessage("Your NFT with ID "+ nft.getNftId() + " is for sale, price: " + nft.getNftPrice());
            onSellMessages.add(onSellMessage);
        }

        return onSellMessages;
    }

    @Override
    public ArrayList<Nft> selectNftsByPages(int pageNumber) {
        return nftDao.fetchNftByPageNumber(pageNumber-1);
    }

    @Override
    public int selectNftsPagesCount() {
        int temp = nftDao.selectNftCount();
        double count = (double) temp / (double) 8;
        double ceil = Math.ceil(count);
        return (int)ceil;
    }

    @Override
    public boolean updateNftLikes(Nft nft) {
        int flag = nftDao.updateNftLikesByNftId(nft);
        return flag == 1;
    }

    @Override
    public ArrayList<FollowInfo> selectNftsByPages(int pageNumber, int userId) {
        ArrayList<FollowInfo> followInfos = new ArrayList<>();
        ArrayList<Nft> nfts = nftDao.selectNftByPageNumber(pageNumber-1,userId);

        for (Nft nft : nfts) {
            FollowInfo followInfo = new FollowInfo();

            User user = userDao.selectUserByUserId(nft.getNftUserId());
            UserDetail userDetail = userDetailDao.selectUserDetailByUserId(nft.getNftUserId());

            followInfo.setNft(nft);
            followInfo.setUser(user);
            followInfo.setUserDetail(userDetail);
            followInfos.add(followInfo);
        }
        return followInfos;
    }

    @Override
    public int selectNftsOnMarketPagesCount(int userId) {
        int temp = nftDao.selectNftOnMarketCount(userId);
        double count = (double) temp / (double) 8;
        double ceil = Math.ceil(count);
        return (int)ceil;
    }

    @Override
    public ArrayList<Nft> getNftRank() {
        return nftDao.selectNftOrderByLike();
    }

}
