package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: community
 * @description: 系统通知事件实体
 * @author: Macchac
 * @create: 2020-07-16 17:25
 **/
public class Event {

    /**
     * 如张三给李四点赞 entityType为用户 entityId为李四
     * userId为张三 entityUserId为李四
     */
    /**
     * 主题 事件类型
     */
    private String topic;
    /**
     * 事件触发的人
     */
    private int userId;
    /**
     * 事件发生在哪个实体上：类型
     */
    private int entityType;
    /**
     * 事件发生在哪个实体上：id
     */
    private int entityId;
    /**
     * 帖子评论实体的作者
     */
    private int entityUserId;
    /**
     * 事件对象通用性拓展
     */
    private Map<String,Object> data = new HashMap<>();


    public String getTopic() {
        return topic;
    }

    /**
     * 调用set的时候返回实体 设置多个属性时 可以再次调用set
     * @param topic
     * @return
     */
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key ,Object value) {
        this.data.put(key, value);
        return this;
    }
}
