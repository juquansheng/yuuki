package com.malaxiaoyugan.yuukicore.service.impl;

import com.google.common.collect.Lists;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.entity.UserExample;
import com.malaxiaoyugan.yuukicore.entity.UserRole;
import com.malaxiaoyugan.yuukicore.entity.UserRoleExample;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserRoleMapper;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
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

    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    @Override
    public User updateUserLastLoginInfo(User user) {
        if (user != null) {
            //user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginTime(new Date());
           // user.setLastLoginIp(IpUtil.getRealIp(RequestHolder.getRequest()));
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
        return user;
    }

    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<User> getUserByRoleId(Long roleId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
        List<User> userList = Lists.newArrayList();
        for (UserRole userRole : userRoleList){
            userList.add(userMapper.selectByPrimaryKey(userRole.getUserId()));
        }
        return userList;
    }
}
