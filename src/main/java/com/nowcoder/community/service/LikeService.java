package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hyc
 * @create 2022-06-25 20:18
 * <p>
 * 使用redis就不用对持久层操作，直接在service中编写对数据的操作
 */
@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void like(int userId, int entityType, int entityId) {
        //每条帖子或者每条评论或者每条回复都有一个它自己的key，以及这个key对应的点赞用户id集合
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);

        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            redisTemplate.opsForSet().remove(entityLikeKey, userId);//用户本来是赞了的状态，这次点是取消赞操作
        } else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);//用户本来是未点赞状态，这次是点赞操作
        }
    }

    //获取点赞数量
    public long findEntityLikeCount(int entityType, int entityId) {
        //拿到这条帖子或评论或回复的key
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //获取点赞状态  1：点赞了 0：没点赞 后续功能扩展可以用-1表示踩
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

}
