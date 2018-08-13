package com.malaxiaoyugan.yuukicore.controller;

import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.framework.property.AppProperties;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.SessionUtil;
import com.malaxiaoyugan.yuukicore.utils.TTBFResult;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.utils.YuukiResult;
import com.malaxiaoyugan.yuukicore.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "passport")
public class PassPortController {

    @Autowired
    private AppProperties config;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
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
}
