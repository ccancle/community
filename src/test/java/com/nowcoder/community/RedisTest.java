package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @program: community
 * @description: Redis测试类
 * @author: Macchac
 * @create: 2020-07-13 18:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void TestString(){
         String redisKey = "test:count";
         redisTemplate.opsForValue().set(redisKey,1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    /**
     * 访问hash
     */
    @Test
    public void TestHash(){
        String reidsKey = "test:user";

        redisTemplate.opsForHash().put(reidsKey,"id",1);
        redisTemplate.opsForHash().put(reidsKey,"username","ZhangSan");

        System.out.println(redisTemplate.opsForHash().get(reidsKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(reidsKey,"username"));
    }

    /**
     * 访问hash
     */
    @Test
    public void TestList(){
        String reidsKey = "test:ids";

        redisTemplate.opsForList().leftPush(reidsKey,101);
        redisTemplate.opsForList().leftPush(reidsKey,102);
        redisTemplate.opsForList().leftPush(reidsKey,103);

        System.out.println(redisTemplate.opsForList().size(reidsKey));
        System.out.println(redisTemplate.opsForList().index(reidsKey,0));
        System.out.println(redisTemplate.opsForList().range(reidsKey,0,2));
//弹出
        System.out.println(redisTemplate.opsForList().leftPop(reidsKey));
        System.out.println(redisTemplate.opsForList().leftPop(reidsKey));
        System.out.println(redisTemplate.opsForList().leftPop(reidsKey));
    }
    /**
     * 集合
     */
    @Test
    public void TestSets(){
        String redisKey = "test:teachers";
        redisTemplate.opsForSet().add(redisKey,"刘备","关羽","张飞","马超","诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }
    /**
     * 有序集合
     */
    @Test
    public void TestSortedSets(){
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey,"唐僧",80);
        redisTemplate.opsForZSet().add(redisKey,"悟空",90);
        redisTemplate.opsForZSet().add(redisKey,"八戒",50);
        redisTemplate.opsForZSet().add(redisKey,"沙僧",70);
        redisTemplate.opsForZSet().add(redisKey,"白龙马",100);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,3));
    }

    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");

        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students",10, TimeUnit.SECONDS);

    }
    /**
     * 多次访问同一个key
     */
    @Test
    public void testBoundOperations(){
        String redisKey = "test:count";
        //反复访问某一个key 设置类型的Operations
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);

        //访问累加方法
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    /**
     * Redis编程式事务
     */
    @Test
    public void  testTransactional(){
        Object object = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String redisKey =   "test:tx";
                //启用事务
                redisOperations.multi();

                //把命令发到队列中 无法查询
                redisOperations.opsForSet().add(redisKey,"zhangsan");
                redisOperations.opsForSet().add(redisKey,"lisi");
                redisOperations.opsForSet().add(redisKey,"huangsaunw");

                System.out.println(redisOperations.opsForSet().members(redisKey));
                //提交事务
                return redisOperations.exec();
            }
        });
        System.out.println(object);
    }

}
