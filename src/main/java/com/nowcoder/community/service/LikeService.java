package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @program: community
 * @description: 点赞服务
 * 点第一次点赞成功，点第二次取消点赞
 * @author: Macchac
 * @create: 2020-07-13 19:10
 **/
@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞
     * @param userId
     * @param entityType
     * @param entityId
     */
    public void like(int userId,int entityType ,int entityId,int entityUserId){
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
//        //判断用户是否存在点赞记录
//        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey,userId);
//        if (isMember){
//            //点过赞 取消点赞 删除redis数据
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }else {
//            //未点赞 点赞成功 添加数据
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                //判断点赞
                boolean isMember = redisOperations.opsForSet().isMember(entityLikeKey,userId);

                redisOperations.multi();
                if (isMember){
                    //点过赞 取消点赞 删除redis数据
                    redisOperations.opsForSet().remove(entityLikeKey,userId);
                    redisOperations.opsForValue().decrement(userLikeKey);
                }else {
                    //未点赞 点赞成功 添加数据
                    redisOperations.opsForSet().add(entityLikeKey,userId);
                    redisOperations.opsForValue().increment(userLikeKey);
                }

                return redisOperations.exec();
            }
        });
    }

    /**
     * 查询实体点赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long findEntityLikeCount(int entityType,int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某个实体的点赞状态
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int findEntityLikeStatus(int userId,int entityType,int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        //1标识点赞 0 标识没有点赞
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId) ? 1 : 0;
    }
    /**
     * 查询用户获得的赞数量
     */
    public int findUserLikeCount(int userId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count==null ? 0 : count.intValue();

    }

}
