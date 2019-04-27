package com.mosquito.async;

import java.util.List;

/**
 */
public interface EventHandler {
    /*
     * @Author mosquito
     * @Description 处理事件
     * @Param [model]
     * @return void
     **/
    void doHandle(EventModel model);

    /*
     * @Author mosquito
     * @Description 注册事件
     * @Param []
     * @return java.util.List<com.mosquito.async.EventType>
     **/
    List<EventType> getSupportEventTypes();
}
