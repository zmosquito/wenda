package com.mosquito.dao;

import com.mosquito.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    /**select message.*,tt.cnt
     from message,(
     select conversation_id,count(id) as cnt
     from message
     group by conversation_id) tt
     where created_date in (
     select max(created_date)
     from message
     group by conversation_id)
     and message.conversation_id=tt.conversation_id
     order by created_date desc
     limit 0, 2;
     * */
    @Select({"select tt.id, from_id, to_id, content, created_date, has_read, message.conversation_id from message,(select conversation_id,count(id) as id from message group by conversation_id) tt where created_date in (select max(created_date) from message group by conversation_id) and message.conversation_id = tt.conversation_id and (from_id = #{userId} or to_id = #{userId}) order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
}
