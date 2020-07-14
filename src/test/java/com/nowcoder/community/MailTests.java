package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/22 19:15
 * @description：TODO
 * @version: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("821161464@qq.com","测试样例1","没啥 就一个简单的。。邮件后台。。。");
    }

    @Test
    public void testHtmlMail(){
        Context context  = new Context();
        context.setVariable("username","maccha");

        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);

        mailClient.sendMail("821161464@qq.com","HTML",content);
    }

    public String getCode(int length){
        String code = "";
        for(int i=0;i<length;i++){
            boolean boo = (int)(Math.random()*2)==0;
            if(boo){
                code += String.valueOf((int)(Math.random()*10));
            }else {
                int temp = (int)(Math.random()*2)==0?65:97;
                char ch = (char)(Math.random()*26+temp);
                code += String.valueOf(ch);
            }
        }
        return code;
    }

    @Test
    public void PrintCode(){
        System.out.println(getCode(6));
        System.out.println(getCode(6));
        System.out.println(getCode(6));
    }


}
