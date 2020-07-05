package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/12 20:35
 * @description：TODO
 * @version: TODO
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTests {
    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1(){
        Object object = alphaService.savel();
        System.out.println(object);

    }

    @Test
    public void testSave2(){
        Object object = alphaService.save2();
        System.out.println(object);
    }

    @Test
    public void testSave3(){
        Object object = alphaService.save3();
        System.out.println(object);
    }

    @Test
    public void testSave4(){
        Object object = alphaService.save4();
        System.out.println(object);
    }

}
