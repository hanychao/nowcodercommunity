package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hyc
 * @create 2022-06-11 16:15
 */
@Mapper
public interface CommentMapper {

    //返回某条 帖子或评论等 的评论集合 （以分页形式返回）
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //返回某条 帖子或评论等 的评论数量
    int selectCountByEntity(int entityType, int entityId);

    //插入评论
    int insertComment(Comment comment);

    Comment selectCommentById(int id);
}
