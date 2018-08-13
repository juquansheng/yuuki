package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.User;

import java.util.List;

public interface UserService {
    User getUserByName(String name);
    User getUserById(Long id);
    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    User updateUserLastLoginInfo(User user);
    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> getUserByRoleId(Long roleId);
}
