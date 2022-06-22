package com.nowcoder.community.dao;


import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hyc
 * @create 2022-06-21 21:48
 */
@Mapper
public interface MessageMapper {

    //用户的会话列表，针对每个会话只返回最新的一条私信
    List<Message> selectConversations(int userId, int offset, int limit);

    //用户的会话数量
    int selectConversationCount(int userId);

    //某个会话包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //某个会话包含的私信数量
    int selectLetterCount(String conversationId);

    //用户的未读私信数量；当conversationId不为空时，表示用户的某个会话的未读私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //新增一条消息
    int insertMessage(Message message);

    //将当前私信详情页面的未读私信更新为已读
    int updateStatus(List<Integer> ids, int status);
}
