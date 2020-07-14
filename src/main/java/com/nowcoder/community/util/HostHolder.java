package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/6 12:32
 * @description：持有用户信息 用于代替session对象
 * @version: TODO
 */
@Component
public class HostHolder {
    /**
     * 以线程为key 使用currentThread获取当前线程 实现线程分离
     */
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
