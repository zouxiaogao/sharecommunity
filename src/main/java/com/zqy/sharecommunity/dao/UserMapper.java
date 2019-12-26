package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User selectByUserId(@Param("userId") Integer userId);


}