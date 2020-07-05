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
    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset , int limit);

    int selectCountByEntity(int entityType,int entityId);


}
