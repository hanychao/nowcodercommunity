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

    int ENTITY_TYPE_USER = 3;//实体类型：用户

    String TOPIC_COMMENT = "comment";//主题：评论

    String TOPIC_LIKE = "like";//主题：点赞

    String TOPIC_FOLLOW = "follow";//主题：关注

    int SYSTEM_USER_ID = 1;//系统用户ID

    /**
     * 权限: 普通用户
     */
    String AUTHORITY_USER = "user";

    /**
     * 权限: 管理员
     */
    String AUTHORITY_ADMIN = "admin";

    /**
     * 权限: 版主
     */
    String AUTHORITY_MODERATOR = "moderator";


}
