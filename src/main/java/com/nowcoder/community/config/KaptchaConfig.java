package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/24 19:59
 * @description：TODO
 * @version: TODO
 */

@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer(){
        Properties properties = new Properties();
        //宽度
        properties.setProperty("kaptcha.image.width","100");
        //高度
        properties.setProperty("kaptcha.image.height","40");
//        字体大小
        properties.setProperty("kaptcha.textproducer.font.size","40");
//        字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
//        验证码中的内容字符
        properties.setProperty("kaptcha.textproducer.char.String","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//        验证码长度
        properties.setProperty("kaptcha.textproducer.char.length","4");
//        验证码噪点干扰类类型
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
