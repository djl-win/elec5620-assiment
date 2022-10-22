package com.group3.service.impl;

import com.group3.dao.NftDao;
import com.group3.domain.Nft;
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
    private Base64ToFile base64ToFile;

    //生成uuid工具类
    @Autowired
    private CreateUuid createUuid;

    @Override
    public Boolean generateNft(String base64, int userId) {

        //新建一个uuid作为nft的唯一标识符号
        String signature = createUuid.newUuid();

        //文件生成的目录
        String nftImageUrl = "D:/front-end/hkzf/src/assets/nftWorks/" + signature + ".png";

        //1.把base64转为图片保存到本地，并返回地址值
        base64ToFile.saveBase64(base64,nftImageUrl);

        //2.把地址值等信息，传入到数据库
        Nft nft = new Nft();
        nft.setNftSignature(signature);
        nft.setNftUrl(signature + ".png");
        nft.setNftPrice(0);
        nft.setNftDescription("not yet config");
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

}
