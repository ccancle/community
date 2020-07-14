package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/15 10:26
 * @description：TODO
 * @version: TODO
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser(){
        User user=userMapper.selectById(101);
        System.out.println(user);

        user=userMapper.selectByName("liubei");
        System.out.println(user);

        user=userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("maccha");
        user.setPassword("5555");
        user.setSalt("10000");
        user.setEmail("821161464@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(user.getId());
        System.out.println(rows);
    }

    @Test
    public void updateUser(){

        int rows = userMapper.updateStatus(150,1);
        System.out.println(rows);

        rows = userMapper.updateHeader(150,"https://macchac.com/images/avatar.gif");
        System.out.println(rows);

        rows = userMapper.updatePassword(150,"hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for(DiscussPost post : list) {
            System.out.println(post);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1);
        loginTicket.setTicket("adjui");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10*10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("adjui");
        System.out.println(loginTicket);

//        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket("hah");
//        System.out.println(loginTicket1);

        loginTicketMapper.updateStatus("adjui",1);
        loginTicket = loginTicketMapper.selectByTicket("adjui");
        System.out.println(loginTicket);

//        loginTicketMapper.updateStatus("hah",0);
//        loginTicket1 = loginTicketMapper.selectByTicket("hah");
//        System.out.println(loginTicket1);
    }

//    私信列表MapperSql测试
    @Test
    public void testSelectLetter(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        List<Message> list1 = messageMapper.selectLetters("111_112",0,10);
        for (Message message:list1){
            System.out.println(message);
        }
        /**信息数量*/
        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        /**未读信息数量*/
        count = messageMapper.selectLetterUnreadCount(131,"111_131");
        System.out.println(count);
    }
}
