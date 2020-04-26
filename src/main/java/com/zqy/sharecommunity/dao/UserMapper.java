package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User selectByUserId(@Param("userId") Integer userId);

    List<User> selectUsers(@Param("title")String title);

    User selectByName(String username);

    User selectByEmail(String email);

    void insertUser(User user);

    void updateStatus(@Param("userId") int userId, @Param("status")int status);

    int updateHeader(@Param("userId") int userId, @Param("headerUrl") String headUrl);

    int updatePassword(@Param("password")String password,@Param("userId")int userId);
}