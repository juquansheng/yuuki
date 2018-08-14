package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
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

    /**
     * 添加用户
     * @param user
     * @return
     */
    User insertUser(User user);
}
