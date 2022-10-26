package com.group3.dao;

import com.group3.domain.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface FollowDao {

    ArrayList<Follow> findAllFollowsByUserId(@Param("userid") int userId);

    int findNumberOfNftFollowedByUserId(@Param("userid") int userId);

    int deleteFollowByFollowId(@Param("followid")int followId);

    int findAllFollowsByUserIdAndNftId(@Param("userid")int userId, @Param("nftid")int nftId);

    int insertFollowByUserIdAndNftId(@Param("userid")int userId, @Param("nftid")int nftId);
}
