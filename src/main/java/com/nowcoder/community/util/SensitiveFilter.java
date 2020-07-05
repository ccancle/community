package com.nowcoder.community.util;


import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.text.normalizer.Trie;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/12/6 12:48
 * @description：TODO
 * @version: TODO
 */

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init(){
        try(
                InputStream is =this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader  = new BufferedReader(new InputStreamReader(is));
        ){
            String keyword;
            while ( (keyword = reader.readLine()) != null){
                //添加到前缀树
                this.addKeyword(keyword);
            }
        }catch (IOException e){
            logger.error("加载敏感词文件失败"+e.getMessage());
        }

    }

    //讲一个敏感词添加到前缀树中
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length() ; i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null){
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            //指针指向子节点 进入下一轮循环
            tempNode = subNode;

            //设置结束标识
            if (i==keyword.length() - 1){
                tempNode.setKetwordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过虑的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }
//        指针1
        TrieNode tempNode  = rootNode;
//        指针2
        int begin = 0;
//        指针3
        int position = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while ( position < text.length()){
            char c = text.charAt(position);

            //跳过符号
            if (isSymbol(c)){
                //若指针处于根节点
                if (tempNode ==rootNode){
                    stringBuilder.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            //检查下个节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                //以begin为开头的字符串不是敏感词
                stringBuilder.append(text.charAt(begin));
                //进入下一个位置
                position = ++begin;
                //重新指向根节点
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd){
                //发现了敏感词 将begin~position字符串替换掉
                stringBuilder.append(REPLACEMENT);
                //进入下一个位置
                begin = ++position;
                //重新指向根节点
                tempNode = rootNode;
            }else {
                //检查下一个字符
                position++;
            }
        }
        //将最后一批字符 计入结果
        stringBuilder.append(text.substring(begin)) ;

        return stringBuilder.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        //c<0x2E80 || c > 0x9FFF 是东亚文字范围
        return Pattern.matches("[^0-9a-zA-Z\u4e00-\u9fa5]", ""+c);
    }


    //前缀树
    private class TrieNode{
//        关键词结束标识
        private boolean isKeywordEnd = false;

        //子节点(key是下级字符,value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKetwordEnd(){
            return isKeywordEnd;
        }
        public void setKetwordEnd(boolean keywordEnd){
            isKeywordEnd = keywordEnd;
        }

        //添加子节点
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c,node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }

    }
}
