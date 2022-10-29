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
     * Help users create nft and save it to the database url address
     * Interface Address: http://localhost:8080/5620/nfts/createNft
     * json data
     * @param input Base64 encoding of nft {'nftBase64'：data}
     * @return Returns a success or failure message to the frontend: 10011-OK, 10010-Fail
     */
    @PostMapping("/createNft")
    public Result createNewNft(@RequestBody String input, HttpServletRequest request){

        JSONObject jsonObject = JSONObject.parseObject(input);

        //Get the value, not encapsulated
        String base64 = jsonObject.getString("nftBase64");

        //Determine if the value is null (null returns an error message directly)
        if(base64 == null || "".equals(base64)){
            return new Result(null, Code.INSERT_FAIL,"Empty data, please retry");
        }

        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //Determine if the insertion is successful
        Boolean flag = nftService.generateNft(base64, userId);

        if(flag){
            return new Result(true , Code.INSERT_OK,"Congratulations");
        } else {
            return new Result(false, Code.INSERT_FAIL,"Fail to create Nft");
        }
    }

    /**
     * get request
     * Query all nft information for this user
     * Interface Address: http://localhost:8080/5620/nfts
     * @param request Get the set of user ids
     * @return Wrapped nft information collection
     */
    @GetMapping
    public Result setMailVerificationCode(HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //Execute query operations
        ArrayList<Nft> nftList = nftService.selectAll(userId);

        return new Result(nftList,Code.SELECT_OK,"success search all nfts of this user");
    }

    /**
     * put request
     * List this NFT, price set to the price you want to modify, set the version to 1
     * Interface Address: http://localhost:8080/5620/nfts/sellNft
     * @param nft Information about the nft to be listed
     * @return Successful sale or not
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
     * get request
     * Check the nft being sold by this user
     * Interface address: http://localhost:8080/5620/nfts/onSell
     * @param request Get the set of user ids
     * @return Return information about products being sold by the user
     */
    @GetMapping("/onSell")
    public Result onSell(HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //Execute query operations
        ArrayList<OnSellMessage> onSell = nftService.selectAllNftOnSell(userId);
        return new Result(onSell,Code.SELECT_OK,"success");
    }

    /**
     * get request
     * Paging query NFT information, pass in the number of pages
     * @param pageNumber Number of pages queried
     * @return Wrapped nft information collection
     */
    @GetMapping("{pageNumber}")
    public Result getNftByPageNumber(@PathVariable("pageNumber") int pageNumber){
        //Execute query operations
        ArrayList<Nft> nftList = nftService.selectNftsByPages(pageNumber);

        return new Result(nftList,Code.SELECT_OK,"success search");
    }

    /**
     * get Query nft page number
     * @return total pages of nft
     */
    @GetMapping("/count")
    public Result getNftPageCount(){
        // search
        int count = nftService.selectNftsPagesCount();
        return new Result(count,Code.SELECT_OK,"success search");
    }

    /**
     * get NFT likes + 1
     * @return total pages of nft
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
     * get request
     * Paging query for NFT information in the market, pass in the number of pages
     * @param pageNumber Number of pages queried
     * @return Wrapped nft information collection
     */
    @GetMapping("/market/{pageNumber}")
    public Result getMarketNftByPageNumber(@PathVariable("pageNumber") int pageNumber, HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //Execute query operations
        ArrayList<FollowInfo> nftList = nftService.selectNftsByPages(pageNumber,userId);
        return new Result(nftList,Code.SELECT_OK,"success search");
    }

    /**
     * get Query the number of nft pages in the market
     * @return nft total pages
     */
    @GetMapping("/market/count")
    public Result getNftOnMarketPageCount(HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //Execute query operations
        int count = nftService.selectNftsOnMarketPagesCount(userId);
        return new Result(count,Code.SELECT_OK,"success search");
    }

    /** Interface address: http://localhost:8080/5620/nfts/rank
     * get request
     * get Check nft ranking
     * @return nft info dto
     */
    @GetMapping("/rank")
    public Result getNftRank(){
        ArrayList<Nft> rank = nftService.getNftRank();
        return new Result(rank,Code.SELECT_OK,"ok");
    }

    /**
     * get request
     * Search nft by keyword
     * Interface address: http://localhost:8080/5620/follows/search/{keyword}
     * @param keyword Get all eligible nft
     * @return Returns information about all nft that the user follows. {follow,nft,userDetail}
     */
    @GetMapping("/search/{keyword}")
    public Result searchByKeyWord(@PathVariable String keyword){

//        ArrayList<FollowInfo> followInfos = nftService.fetchFollowsByKeyword(keyword);
        System.out.println(keyword);
//        return new Result(followInfos, Code.SELECT_OK,"success");
        return null;
    }
}
