package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hyc
 * @create 2022-06-07 16:13
 */
@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "***";//替换符

    TrieNode root = new TrieNode();//初始化前缀树根节点

    /**
     * 过滤敏感词的方法
     *
     * @param text 待过滤文本
     * @return 过滤后文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        TrieNode cur = root;//敏感词前缀树中的指针
        int begin = 0;//待过滤文本中的慢指针
        int position = 0;//待过滤文本中的快指针

        while (begin < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {
                if (cur == root) {//若指针cur处于根节点,将此特殊符号计入结果,让指针begin向下走一步
                    sb.append(c);
                    begin++;
                }
                position++;//无论特殊符号在开头或中间,指针position都向下走一步
                continue;
            }

            //检查下级节点
            cur = cur.getSubNode(c);
            if (cur == null) {
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                //进入下一个位置
                begin++;
                position = begin;
                //重新指向根节点
                cur = root;
            } else if (cur.isKeyWordEnd) {
                //发现敏感词,将begin~position字符串替换掉
                sb.append(REPLACEMENT);
                //进入下一个位置
                position++;
                begin = position;
                //重新指向根节点
                cur = root;
            } else {
                if (position < text.length() - 1) {
                    position++;
                } else if (position == text.length() - 1) {
                    sb.append(begin);
                    begin++;
                    position = begin;
                    cur = root;
                }
            }
        }
        return sb.toString();
    }
    //判断是否为特殊符号 为特殊符号则返回true
    private boolean isSymbol(Character c) {
        // 0x2E80-0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    /**
     * 初始化前缀树
     * 服务启动时容器就会初始化SensitiveFilter这个bean。
     * 容器初始化这个bean之后，带PostConstruct注解的这个方法会被自动调用。
     */
    @PostConstruct
    public void init() {
        try (
                //字节流->字符流->缓冲流  缓冲流速度比较快
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isr);
        ) {
            String keyWord;
            while ((keyWord = bufferedReader.readLine()) != null) {//一行一行读，且原敏感词文件中一行只有一个敏感词
                insertKeyWord(keyWord);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }

    }
    //往前缀树中插入敏感词
    private void insertKeyWord(String keyWord) {
        TrieNode cur = root;
        for (char c : keyWord.toCharArray()) {
            if (cur.getSubNode(c) == null) {
                cur.addSubNode(c, new TrieNode());
            }
            cur = cur.getSubNode(c);
        }
        cur.setKeyWordEnd(true);//设置结束标志，表示该节点为敏感词的结尾
    }


    //前缀树内部类
    private class TrieNode {
        private Map<Character, TrieNode> subNodes = new HashMap<>();
        private boolean isKeyWordEnd;//默认false

        //添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
    }
}
