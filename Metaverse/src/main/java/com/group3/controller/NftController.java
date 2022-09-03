package com.group3.controller;

import com.alibaba.fastjson.JSONObject;
import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.Nft;
import com.group3.domain.User;
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
}
