package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: community
 * @description: 点赞
 * @author: Macchac
 * @create: 2020-07-14 12:34
 **/
@Controller
public class LikeController implements CommunityConstant {
    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    /**
     * 点赞
     * @param entityType
     * @param entityId
     * @param entityUserId
     * @return
     */
    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId ,int postId){
        User user = hostHolder.getUser();
        //点赞
        likeService.like(user.getId(),entityType,entityId,entityUserId);
        //获取数量
        long likeCount = likeService.findEntityLikeCount(entityType,entityId);
        //状态 前端判断显示状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);

        Map<String,Object> map = new HashMap<>();
        map.put("likeCount" ,likeCount);
        map.put("likeStatus",likeStatus);

        //只有点赞的时候 才触发点赞事件
        if (likeStatus == 1){
            //构造Event事件
            Event event = new Event().setTopic(TOPIC_LIKE)
                    .setEntityType(entityType)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId",postId);
            //触发事件
            eventProducer.fireEvent(event);
        }

        return CommunityUtil.getJSONString(0,null,map);
    }
}
