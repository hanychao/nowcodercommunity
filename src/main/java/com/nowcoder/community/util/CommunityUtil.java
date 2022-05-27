package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @author hyc
 * @create 2022-05-24 21:53
 */
public class CommunityUtil {
    //生成随机字符串，后面生成激活码或者生成salt时可用
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }else{
            return DigestUtils.md5DigestAsHex(key.getBytes());
        }
    }
}
