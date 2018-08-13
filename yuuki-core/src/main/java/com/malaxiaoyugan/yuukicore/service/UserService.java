package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.User;

public interface UserService {
    User getUserByName(String name);
    User getUserById(Long id);
}
