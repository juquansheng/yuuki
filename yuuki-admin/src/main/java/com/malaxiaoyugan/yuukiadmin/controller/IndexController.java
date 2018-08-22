package com.malaxiaoyugan.yuukiadmin.controller;

import com.google.common.collect.Lists;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseVO test(HttpServletRequest request, HttpServletResponse response) {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        long id = Long.parseLong(principals.toString());
        User user = new User();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();

        user.setId(1L);
        user.setPhone("123456");
        user.setNickName("yuuki");

        user1.setId(1L);
        user1.setPhone("123456");
        user1.setNickName("yuuki");

        user2.setId(1L);
        user2.setPhone("123456");
        user2.setNickName("yuuki");

        user3.setId(1L);
        user3.setPhone("123456");
        user3.setNickName("yuuki");

        user4.setId(1L);
        user4.setPhone("123456");
        user4.setNickName("yuuki");

        user5.setId(1L);
        user5.setPhone("123456");
        user5.setNickName("yuuki");

        user6.setId(1L);
        user6.setPhone("123456");
        user6.setNickName("yuuki");
        List<User> list = Lists.newArrayList();
        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);
        return TTBFResultUtil.success("测试页面",list);

    }
}
