package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/18 18:18
 * @description：TODO
 * @version: TODO
 */
@Mapper
@Repository
public interface DiscussPostMapper {

    /**
     * 分页查询帖子
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * @Param注解用于给参数取别名,
     如果只有一个参数,并且在<if>里使用,则必须加别名.
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 发布帖子
     * @param discussPost
     * @return
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * 根据帖子id精准查询
     * @param id
     * @return
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * 更新帖子评论数量
     * @param id
     * @param commentCount
     * @return
     */
    int updateCommentCount(int id,int commentCount);
}