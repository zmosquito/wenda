package com.mosquito.dao;

import com.mosquito.model.Feed;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 
 * 
 */
@Repository
@Mapper
public interface FeedDAO {
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    /**
     * @Author mosquito
     * @Description feed中新鲜事三种模式的推模式
=     * @Param [id]
     * @return com.mosquito.model.Feed
     **/
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed getFeedById(int id);

    /**
     * @Author mosquito
     * @Description feed中新鲜事三种模式的拉模式，采用动态sql
     * @Param [maxId, userIds, count]
     * @return java.util.List<com.mosquito.model.Feed>
     **/
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
