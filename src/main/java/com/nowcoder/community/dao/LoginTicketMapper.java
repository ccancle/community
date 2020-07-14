package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/25 17:16
 * @description：TODO
 * @version: TODO
 */
@Mapper
@Repository
public interface LoginTicketMapper {

    /**
     * 插入登录凭证
     * @param loginTicket
     * @return
     */
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //声明sql机制注解 useGeneraredKeys自动生成主键  注入主键给keyProperty
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 根据ticket字符查询登录凭证数据
     * @param ticket
     * @return 单一不可重复的
     */
    @Select({"select id,user_id,ticket,status,expired ", "from login_ticket where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * 修改凭证状态 退出时候失效
     * @param ticket
     * @param status
     * @return
     */
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
