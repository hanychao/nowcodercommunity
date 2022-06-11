package com.nowcoder.community.util;

/**
 * @author hyc
 * @create 2022-05-27 16:30
 */
public interface CommunityConstant {

    int ACTIVATION_SUCCESS = 0;//激活成功

    int ACTIVATION_REPEAT = 1;//重复激活

    int ACTIVATION_FAILURE = 2;//激活失败

    int DEFAULT_EXPIRED_SECONDS = 3600 * 12; //登陆凭证的默认保存时间

    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 10;//勾选记住我时，登陆凭证的保存时间

    int ENTITY_TYPE_POST = 1;//实体类型：帖子

    int ENTITY_TYPE_COMMENT = 2;//实体类型：评论


}
