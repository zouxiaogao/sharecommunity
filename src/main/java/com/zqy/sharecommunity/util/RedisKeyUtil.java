package com.zqy.sharecommunity.util;

/**
 * @Author zqy
 * @Date 2020/03/20
 */

/**
 * 生成redis key 工具类
 * **/

public class RedisKeyUtil {

    private static final String SPLIT = ":";  //按 ： 拼接
    private static final String PREFIX_ENTITY_LIKE = "like:entity"; //赞前缀
    private static final String PREFIX_USER_LIKE = "like:user"; //点赞用户前缀
    private static final String PREFIX_FOLLOWEE = "followee";  //关注的目标用户前缀
    private static final String PREFIX_FOLLOWER = "follower";  //粉丝用户的前缀
    private static final String PREFIX_KAPTCHA = "kaptcha";  //验证码
    private static final String PREFIX_TICKET = "ticket";   //登录凭证
    private static final String PREFIX_USER = "user";    //登录用户

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)   now : 用时间统计
    // entityType 实体类型 ： 用户 ，帖子，资源..... 后续开发用到
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 用户
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }
}
