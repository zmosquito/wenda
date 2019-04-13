package com.mosquito.service;

import com.mosquito.dao.FeedDAO;
import com.mosquito.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 读取feed
 */
@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    /**
     * @Author mosquito
     * @Description 添加新鲜事到消息队列中
     * @Param [feed]
     * @return boolean
     **/
    public boolean addFeed(Feed feed) {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }
}
