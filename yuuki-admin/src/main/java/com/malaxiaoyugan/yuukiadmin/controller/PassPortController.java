package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.framework.property.AppProperties;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.PasswordUtil;
import com.malaxiaoyugan.yuukicore.utils.SessionUtil;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.LoginVo;
import com.malaxiaoyugan.yuukicore.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "passport")
public class PassPortController {

    @Autowired
    private AppProperties config;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login1",method = RequestMethod.POST)
    public ResponseVO login(@RequestBody LoginVo loginVo, HttpServletRequest request,
                            HttpServletResponse response){
        if (config.getEnableKaptcha()) {
            if (StringUtils.isEmpty(loginVo.getCaptcha()) || !loginVo.getCaptcha().equals(SessionUtil.getKaptcha())) {
                return TTBFResultUtil.error("验证码错误！");
            }
            SessionUtil.removeKaptcha();
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUserName(), loginVo.getPassword(), loginVo.isRememberMe());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
            return TTBFResultUtil.success("登录成功！");
        } catch (Exception e) {
            log.error("登录失败，用户名[{}]", loginVo.getUserName(), e);
            token.clear();
            return TTBFResultUtil.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseVO register(@RequestBody RegisterVo registerVo, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        try {
            //判断用户名是否已经使用
            if (userService.getUserByName(registerVo.getUserName()) == null){
                return TTBFResultUtil.error("用户名已存在");
            }
            User user = new User();
            String alphanumeric = RandomStringUtils.randomAlphanumeric(10);
            user.setHash(alphanumeric);
            user.setCreateTime(new Date());
            user.setMail(registerVo.getMail());
            user.setPhone(registerVo.getPhone());
            user.setUserName(registerVo.getUserName());
            user.setPassword(PasswordUtil.encrypt(registerVo.getPassword(),alphanumeric));
            userService.insertUser(user);
            return TTBFResultUtil.success("注册成功！");
        }catch (Exception e){
            log.error("注册失败", e);
            return TTBFResultUtil.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResponseVO test() {

            return TTBFResultUtil.success("test",userService.getUserById(1L));

    }
}
