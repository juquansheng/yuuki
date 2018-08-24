package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.ProfileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 个人中心相关接口
 */
@Slf4j
@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResponseVO user(HttpServletRequest request, HttpServletResponse response) {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        long id = Long.parseLong(principals.toString());
        User userById = userService.getUserById(id);

        ProfileVo profileVo = new ProfileVo();
        profileVo.setUserName(userById.getUserName());
        profileVo.setNickName(userById.getNickName());
        profileVo.setCreateTime(simpleDateFormat.format(userById.getCreateTime()));
        profileVo.setLastLoginTime(simpleDateFormat.format(userById.getLastLoginTime()));
        profileVo.setPhone(userById.getPhone());
        profileVo.setMail(userById.getMail());


        return TTBFResultUtil.success("成功",profileVo);
    }
}
