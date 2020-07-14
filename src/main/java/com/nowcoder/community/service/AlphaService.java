package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/6 8:31
 * @description：TODO
 * @version: TODO
 */
@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    @Qualifier("alphaHibernate")
    //Qualifier（）加载其他Dao层实现
    private AlphaDao alphaDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DiscussPostMapper discussPostMapper;


    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init (){
        System.out.println("初始化AlphaService ");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁AlphaService");
    }


    public String find(){
        return alphaDao.select();
    }

    /**
     isolation事务隔离级别
     propagation事务传播机制
        REQUIRED(0),***
        SUPPORTS(1),
        MANDATORY(2),
        REQUIRES_NEW(3),***
        NOT_SUPPORTED(4),
        NEVER(5),
        NESTED(6);***
     前两个用的多点
     //REQUIRED: 支持当前事务 (外部事务即调用者)，如果外部事务不存在则创建新事务
     //REQUIRES_NEW：创建一个新的事务 并且暂停当前事务(外部事务)
     //NESTED：如果当前存在事务（外部事务）则嵌套在该事务中执行（有独立的提交和回滚），如果不存在就会和REQUIRED一样
     //业务方法A 调用业务方法B  两个事务交叉 以
   */
    @Transactional(isolation = Isolation.READ_COMMITTED , propagation = Propagation.REQUIRED)
    public Object savel(){
        //新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123")+user.getSalt());
        user.setEmail("alpha@qq.com");
        user.setCreateTime(new Date());
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        userMapper.insertUser(user);

        //新增帖子
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle("Hello！");
        discussPost.setContent("新人报道！！");
        discussPost.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(discussPost);

        Integer.valueOf("abc");
        //发生异常 事务回滚 不把数据录入

        return "OK";
    }

    /**
     * 编程式事务
     * @return
     */
    public Object save2(){
        //隔离级别
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        //传播机制
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);


        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123")+user.getSalt());
                user.setEmail("beta@qq.com");
                user.setCreateTime(new Date());
                user.setHeaderUrl("http://image.nowcoder.com/head/999t.png");
                userMapper.insertUser(user);

                //新增帖子
                DiscussPost discussPost = new DiscussPost();
                discussPost.setUserId(user.getId());
                discussPost.setTitle("你好");
                discussPost.setContent("我是新人！！！！");
                discussPost.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(discussPost);

                Integer.valueOf("abc");
                //发生异常 事务回滚 不把数据录入

                return "OK";
            }
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    public Object save3(){
        User user = new User();
        user.setHeaderUrl("http://image.nowcoder.com/head/11t.png");
        user.setUsername("caicai");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("5555") + user.getSalt());
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        userMapper.updatePassword(user.getId(),CommunityUtil.md5("2333"));

        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle("你好");
        discussPost.setContent("我失信人！！！！");
        discussPost.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(discussPost);
        return "OK";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    public Object save4(){
        User usercaicai  = new User();
        usercaicai = userMapper.selectByName("caicai");
        userMapper.updateStatus(usercaicai.getId(),1);
        return "OK";
    }

}
