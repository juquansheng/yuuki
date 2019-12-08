package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.service.UserLogService;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import com.malaxiaoyugan.yuukicore.vo.ProfileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 个人中心相关接口
 */
@Slf4j
@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private ArticleService articleService;



    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseVO user(@RequestBody User user, @RequestParam("page") Integer page,
                           @RequestParam("rows") Integer rows) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        //参数不带用户id且未登录则返回401未登录
        if (principals == null && StringUtils.isEmpty(user.getId())){
            return TTBFResultUtil.vo(401,"用户未登录",null);
        }
        long id;
        if (StringUtils.isEmpty(user.getId())){
            id = Long.parseLong(principals.toString());
        }else {
            id = user.getId();
        }
        User userById = userService.getUserById(id);


        Article article = new Article();
        article.setUserId(id);
        PageBean pageBean = articleService.getList(article, page, rows);


        ProfileVo profileVo = new ProfileVo();
        profileVo.setPageBean(pageBean);
        profileVo.setId(userById.getId());
        profileVo.setUserName(userById.getUserName());
        profileVo.setNickName(userById.getNickName());
        profileVo.setCreateTime(simpleDateFormat.format(userById.getCreateTime()));
        profileVo.setLastLoginTime(simpleDateFormat.format(userById.getLastLoginTime()));
        profileVo.setPhone(userById.getPhone());
        profileVo.setMail(userById.getMail());


        return TTBFResultUtil.success("成功",profileVo);
    }

    @RequestMapping(value = "/log",method = RequestMethod.GET)
    public ResponseVO log(HttpServletRequest request, HttpServletResponse response,@RequestParam("page") Integer page,
                          @RequestParam("rows") Integer rows) {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        long id = Long.parseLong(principals.toString());
        PageBean pageBean = userLogService.getList(id, page, rows);


        return TTBFResultUtil.success("成功",pageBean);
    }
}
