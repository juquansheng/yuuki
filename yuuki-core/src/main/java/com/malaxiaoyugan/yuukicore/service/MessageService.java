package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Message;

public interface MessageService {
    //新增消息
    void insert(Message message);
    //读取消息
    void readMessage(Long id);
}
