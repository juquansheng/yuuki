package com.malaxiaoyugan.yuukicore.service.impl;

import com.malaxiaoyugan.yuukicore.entity.Message;
import com.malaxiaoyugan.yuukicore.mapper.MessageMapper;
import com.malaxiaoyugan.yuukicore.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public void insert(Message message) {

        message.setStatus(0);
        messageMapper.insertSelective(message);
    }

    @Override
    public void readMessage(Long id) {
        Message message = new Message();
        message.setStatus(1);
        message.setId(id);
        message.setUpdatetime(new Date());
        messageMapper.insertSelective(message);
    }
}
