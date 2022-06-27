package com.nowcoder.community.util;

/**
 * @author hyc
 * @create 2022-06-25 11:37
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    //某个实体的点赞
    //redis的key值形式 like:entity:entityType:entityId
    //redis的value值形式 set<userId>
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
