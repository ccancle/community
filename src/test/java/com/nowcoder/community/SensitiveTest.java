package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/9 20:56
 * @description：TODO
 * @version: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌博，可以嫖娼，可以开票，可以吸毒，哈哈哈哈哈哈";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        String text2 = "这里可以赌※博，可以嫖?娼?，可以开?票，可以吸!毒@，哈哈哈哈哈哈";
        text2 = sensitiveFilter.filter(text2);
        System.out.println(text2);
    }

}
