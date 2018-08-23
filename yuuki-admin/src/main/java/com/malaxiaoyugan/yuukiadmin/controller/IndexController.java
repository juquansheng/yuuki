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

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ResponseVO test(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();


        user.setId(1L);
        user.setPhone("123456");
        user.setNickName("yuuki");


        List<User> list = Lists.newArrayList();
        list.add(user);
        return TTBFResultUtil.success("测试页面",list);

    }
}
