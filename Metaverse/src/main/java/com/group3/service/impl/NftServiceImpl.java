package com.group3.service.impl;

import com.group3.dao.NftDao;
import com.group3.domain.Nft;
import com.group3.service.NftService;
import com.group3.utils.Base64ToFile;
import com.group3.utils.CreateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String nftImageUrl = "D:\\nftImages\\" + signature + ".png";

        //1.把base64转为图片保存到本地，并返回地址值
        base64ToFile.saveBase64(base64,nftImageUrl);

        //2.把地址值等信息，传入到数据库
        Nft nft = new Nft();
        nft.setNftSignature(signature);
        nft.setNftUrl(nftImageUrl);
        nft.setNftPrice(0);
        nft.setNftDescription("not yet config");
        nft.setNftLikes(0);
        nft.setNftUserId(userId);
        return nftDao.insertNft(nft) == 1;
    }
}
