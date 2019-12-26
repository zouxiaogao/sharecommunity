package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.UserMapper;
import com.zqy.sharecommunity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public User findUserByUserId(int userId){
        return userMapper.selectByUserId(userId);
    }
}
