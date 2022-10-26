package com.group3.controller;

import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.dto.OnSellMessage;
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
     * get 查询该用户正在出售的nft 接口地址：http://localhost:8080/5620/follows/getFollowedNfts get请求
     * @param request 获取该用户关注的所有nft信息
     * @return 返回用户关注的所有nft信息，{follow,nft,userDetail}
     */
    @GetMapping("/getFollowedNfts")
    public Result allInformation(HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();
        //执行查询操作
        ArrayList<FollowInfo> followInfos = followService.fetchFollowsByUserId(userId);

        return new Result(followInfos, Code.SELECT_OK,"success");
    }

    /**
     * delete cancel follow 接口地址：http://localhost:8080/5620/follows/cancelFollow delete请求
     * @param followInfo 获取该用户当前点击的的nft信息
     * @return 返回成功与否
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
     * post 新增关注 接口地址：http://localhost:8080/5620/follows
     * @param request 请求
     * @param nft nft
     * @return true or false
     */
    @PostMapping
    public Result allInformation(@RequestBody Nft nft,HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        //执行查询操作 1用户已经follow了此NFT， 2成功， 3失败
        int flag = followService.newFollow(userId,nft);
        if(flag == 1){
            return new Result(null, Code.INSERT_FAIL,"You have already followed this nft");
        } else if(flag == 3){
            return new Result(null, Code.INSERT_FAIL,"fail to follow");
        }

        return new Result(null, Code.INSERT_OK,"success");
    }
}
