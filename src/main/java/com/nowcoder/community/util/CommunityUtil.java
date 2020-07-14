package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/23 17:35
 * @description：工具类
 * @version: TODO
 */

public class CommunityUtil {

    /**
     * 生成UUID随机字符串
     * */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成验证码
     * @param length
     * @return
     */
    public static String getCode(int length){
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
    /**
     * MD5加密
        hello -> abc123def456
        hello + 3e4a8 -> abc123def456abc
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if (map!=null){
            for (String key:map.keySet()) {
                jsonObject.put(key,map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }
    public static String getJSONString (int code,String msg){
        return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",25);
        System.out.println(getJSONString(0,"ok",map));
    }

}