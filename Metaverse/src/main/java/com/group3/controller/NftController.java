package com.group3.controller;

import com.alibaba.fastjson.JSONObject;
import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.dto.OnSellMessage;
import com.group3.service.NftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/nfts")
public class NftController {

    @Autowired
    private NftService nftService;

    /**
     * 帮助用户创建nft，并保存到数据库url地址  接口地址：http://localhost:8080/5620/nfts/createNft  json数据
     * @param input nft的base64编码 {'nftBase64'：data}
     * @return 返回给前台创建成功信息，或失败  10011-OK 10010-Fail
     */
    @PostMapping("/createNft")
    public Result createNewNft(@RequestBody String input, HttpServletRequest request){

        JSONObject jsonObject = JSONObject.parseObject(input);

        //获取数值，不封装了
        String base64 = jsonObject.getString("nftBase64");

        //判断是否为空值（为空直接返回错误信息）
        if(base64 == null || "".equals(base64)){
            return new Result(null, Code.INSERT_FAIL,"Empty data, please retry");
        }

        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //判断是否插入成功
        Boolean flag = nftService.generateNft(base64, userId);

        if(flag){
            return new Result(true , Code.INSERT_OK,"Congratulations");
        } else {
            return new Result(false, Code.INSERT_FAIL,"Fail to create Nft");
        }
    }

    /**
     * get 请求查询此用户所有nft信息 接口地址：http://localhost:8080/5620/nfts  get请求
     * @param request 获取用户id的集合
     * @return 封装好的nft信息集合
     */
    @GetMapping
    public Result setMailVerificationCode(HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //执行查询操作
        ArrayList<Nft> nftList = nftService.selectAll(userId);

        return new Result(nftList,Code.SELECT_OK,"success search all nfts of this user");
    }

    /**
     * put 挂售此NFT,price设置为想要修改的价格，把version设置为1 接口地址：http://localhost:8080/5620/nfts/sellNft  put请求
     * @param nft 要挂售的nft的信息
     * @return 成功出售与否
     */
    @PutMapping("/sellNft")
    public Result sellNft(@RequestBody Nft nft){
        Boolean aBoolean = nftService.pushNft(nft);
        if(aBoolean){
            return new Result(true, Code.UPDATE_OK, "You have successfully placed the sale!");
        } else {
            return new Result(false, Code.UPDATE_FAIL, "Fail to sell!");
        }
    }

    /**
     * get 查询该用户正在出售的nft 接口地址：http://localhost:8080/5620/nfts/onSell get请求
     * @param request 获取用户id的集合
     * @return 返回用户正在出售的商品信息
     */
    @GetMapping("/onSell")
    public Result onSell(HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //执行查询操作
        ArrayList<OnSellMessage> onSell = nftService.selectAllNftOnSell(userId);
        return new Result(onSell,Code.SELECT_OK,"success");
    }

    /**
     * get 分页查询NFT信息，传入页数
     * @param pageNumber 查询的页数
     * @return 封装好的nft信息集合
     */
    @GetMapping("{pageNumber}")
    public Result getNftByPageNumber(@PathVariable("pageNumber") int pageNumber){
        //执行查询操作
        ArrayList<Nft> nftList = nftService.selectNftsByPages(pageNumber);

        return new Result(nftList,Code.SELECT_OK,"success search");
    }

    /**
     * get 查询nft页数
     * @return nft总页数
     */
    @GetMapping("/count")
    public Result getNftPageCount(){
        //执行查询操作
        int count = nftService.selectNftsPagesCount();
        return new Result(count,Code.SELECT_OK,"success search");
    }

    /**
     * get NFT likes + 1
     * @return nft总页数
     */
    @PostMapping("/likes")
    public Result handleNftLikes(@RequestBody Nft nft){
        boolean flag = nftService.updateNftLikes(nft);
        if(flag){
            return new Result(true,Code.UPDATE_OK,"success update likes");
        }
        return new Result(false,Code.UPDATE_FAIL,"fail to update likes");
    }


    /**
     * get 分页查询市场中的NFT信息，传入页数
     * @param pageNumber 查询的页数
     * @return 封装好的nft信息集合
     */
    @GetMapping("/market/{pageNumber}")
    public Result getMarketNftByPageNumber(@PathVariable("pageNumber") int pageNumber, HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //执行查询操作
        ArrayList<FollowInfo> nftList = nftService.selectNftsByPages(pageNumber,userId);
        return new Result(nftList,Code.SELECT_OK,"success search");
    }

    /**
     * get 查询市场中nft页数
     * @return nft总页数
     */
    @GetMapping("/market/count")
    public Result getNftOnMarketPageCount(HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //执行查询操作
        int count = nftService.selectNftsOnMarketPagesCount(userId);
        return new Result(count,Code.SELECT_OK,"success search");
    }
}
