package com.nowcoder.community.util;

/**
 * @program: community
 * @description: RedisKey工具类
 * @author: Macchac
 * @create: 2020-07-13 19:03
 **/
public class RedisKeyUtil {
    /**
     * 以冒号分割
     */
    private static final String SPLIT = ":";
    /**
     * 实体赞的前缀
      */
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    /**
     * 某一个用户的赞
     */
    private static final String PREFIX_USER_LIKE = "like:user";
    /**
     * 被关注的对象
     */
    private static final String PREFIX_FOLLOWEE ="followee";
    /**
     * 关注者
     */
    private static final String PREFIX_FOLLOWER ="follower";
    /**
     * 验证码
     */
    private static final String PREFIX_KAPTCHA = "kaptcha";
    /**
     * 登录凭证
     */
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";

    /**
     * 生成某个实体的赞
     * 拼接成：like:entity:entityType:entityId  ->
     * 存储类型set(userId)
     * 以便于日后能看到谁点赞了
     */
    public static String getEntityLikeKey(int entityType ,int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 某一个用户的赞
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体
     */
    //followee:userId:entityType  -> zSet(entityId,now)
    public static String getFolloweeKey(int userId,int entityType){
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }
    /**
     * 某个实体拥有的粉丝
     *follower:entityType:entityId  -> zSet(userId,now)
     */
    public static String getFollowerKey(int entityType,int entityId){
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 登录验证码:识别验证码属于哪个用户
     */
    public static String getKaptchaKey(String owner){
        return PREFIX_KAPTCHA + SPLIT + owner;
    }
    /**
     * 登录凭证
     */
    public static String getTicketKey(String ticket){
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 查询用户
     * @param userId
     * @return
     */
    public static String getUserKey(int userId){
        return PREFIX_USER + SPLIT + userId;
    }
}
