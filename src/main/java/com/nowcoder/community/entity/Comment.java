package com.nowcoder.community.entity;


import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/13 18:24
 * @description：TODO
 * @version: TODO
 */
public class Comment {
    private int id;
    private int userId;
    /**评论实体类型（1-评论的是帖子 2-评论的是回帖或者用户）*/
    private int entityType;
    /**
     * 评论目标
     * 如果是帖子 则指的是帖子Id 如果是回帖则指的是评论Id'
     * */
    private int entityId;
    /**回复指向的用户Id*/
    private int targetId;
    /**评论内容*/
    private String content;
    /**状态 0-正常 1-删除*/
    private int status;
    /**创建时间*/
    private Date createTime;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
