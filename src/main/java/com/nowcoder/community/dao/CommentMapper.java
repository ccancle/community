package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/13 18:26
 * @description：TODO
 * @version: TODO
 */
@Mapper
public interface CommentMapper {
    /**
     * 根据实体查询 (是帖子的评论还是回帖的评论还是)
     * @param entityType 实体类型
     * @param entityId 实体Id
     * @param offset 分页条件
     * @param limit 行数限制
     * @return
     */
    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset , int limit);

    /**
     * 查询数据条目数
     * @param entityType 实体类型
     * @param entityId 实体Id
     * @return
     */
    int selectCountByEntity(int entityType,int entityId);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int insertComment(Comment comment);


    Comment selectCommentById(int id);

}
