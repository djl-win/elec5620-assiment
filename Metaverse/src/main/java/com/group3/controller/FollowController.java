package com.group3.controller;

import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * Query the NFT this user is following
     * Interface address: ttp://localhost:8080/5620/follows/getFollowedNfts
     * get request
     * @param request Get all the NFT information that this user is following
     * @return Returns information about all NFTs that the user follows.{follow,nft,userDetail}
     */
    @GetMapping("/getFollowedNfts")
    public Result allInformation(HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //Execute query operations
        ArrayList<FollowInfo> followInfos = followService.fetchFollowsByUserId(userId);

        return new Result(followInfos, Code.SELECT_OK,"success");
    }


    /**
     * cancel follow
     * Interface address: http://localhost:8080/5620/follows/cancelFollow
     * delete request
     * @param followInfo Get the NFT information of the user's current click
     * @return Return Success or Failure
     */
    @DeleteMapping("/cancelFollow")
    public Result cancelFollow(@RequestBody FollowInfo followInfo){
        System.out.println(followInfo);
        boolean flag  = followService.cancelFollowByFollowId(followInfo.getFollow().getFollowId());
        if(flag){
            return new Result(true, Code.DELETE_OK,"success");
        } else {
            return new Result(false, Code.DELETE_FAIL, "Fail to cancel follow");
        }
    }

    /**
     * post request
     * follow
     * Interface address：http://localhost:8080/5620/follows
     * @param request Request
     * @param nft nft
     * @return true or false
     */
    @PostMapping
    public Result allInformation(@RequestBody Nft nft,HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //Execute query operation 1 user has followed this NFT, 2 success, 3 failure
        int flag = followService.newFollow(userId,nft);
        if(flag == 1){
            return new Result(null, Code.INSERT_FAIL,"You have already followed this nft");
        } else if(flag == 3){
            return new Result(null, Code.INSERT_FAIL,"fail to follow");
        }

        return new Result(null, Code.INSERT_OK,"success");
    }
}
