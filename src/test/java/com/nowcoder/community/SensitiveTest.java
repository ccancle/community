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
        String text = "我爱女人爱过滤棒子，仓井空很好看，志忠喜欢逛色情网站 哈哈哈哈哈";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        String text2 = "我操爱女※人※爱过※，仓※井?空很好看，志忠喜@欢逛!色情网站 哈哈哈哈哈";
        text2 = sensitiveFilter.filter(text2);
        System.out.println(text2);
    }

}
