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
    private static final String SPLIT = ",";
    /**
     * 实体赞的前缀
      */
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    /**
     * 某一个用户的赞
     */
    private static final String PREFIX_USER_LIKE = "like:user";

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

}
