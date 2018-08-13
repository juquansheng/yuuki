package com.malaxiaoyugan.yuukicore.service.impl;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.entity.UserExample;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByName(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(name).andStateEqualTo(0);
        List<User> users = userMapper.selectByExample(userExample);
        if (!ListUtils.isEmpty(users)){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }
}
