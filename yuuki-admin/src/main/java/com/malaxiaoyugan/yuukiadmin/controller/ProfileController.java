package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人中心相关接口
 */
@Slf4j
@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResponseVO user(HttpServletRequest request, HttpServletResponse response) {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        long id = Long.parseLong(principals.toString());
        User userById = userService.getUserById(id);
        User user = new User();
        user.setUserName(userById.getUserName());
        user.setNickName(userById.getNickName());
        return TTBFResultUtil.success("成功",user);
    }
}
