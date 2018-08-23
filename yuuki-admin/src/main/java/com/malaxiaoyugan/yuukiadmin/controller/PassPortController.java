package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.constants.PassPortConst;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.framework.property.AppProperties;
import com.malaxiaoyugan.yuukicore.service.RedisService;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.service.VerificationCodeService;
import com.malaxiaoyugan.yuukicore.utils.PasswordUtil;
import com.malaxiaoyugan.yuukicore.utils.SessionUtil;
import com.malaxiaoyugan.yuukicore.utils.TTBFResult;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.LoginVo;
import com.malaxiaoyugan.yuukicore.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *@describe
 *@author  ttbf
 *@date  2018/8/15
 */
@Slf4j
@RestController
@RequestMapping(value = "passport")
public class PassPortController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private VerificationCodeService verificationCodeService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseVO login(@RequestBody LoginVo loginVo, HttpServletRequest request,
                            HttpServletResponse response){

        String verifyCode = redisService.get(PassPortConst.VERIFYCODE_KEY + loginVo.getVerifyCodeKey(), PassPortConst.VERIFYCODE_KEY);

        if (StringUtils.isEmpty(loginVo.getVerifyCodeKey()) || StringUtils.isEmpty(verifyCode) ||
                !loginVo.getVerifyCode().toLowerCase().equals(verifyCode.toLowerCase())) {
            return TTBFResultUtil.error("验证码错误！");
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

        boolean checkVerifyCode = verificationCodeService.checkVerifyCode(registerVo.getPhone(), registerVo.getCaptcha());
        if (!checkVerifyCode){
            return TTBFResultUtil.error("验证码错误");
        }
        try {
            //判断用户名是否已经使用
            if (userService.getUserByName(registerVo.getUserName()) != null){
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
            user.setUpdateTime(new Date());
            user.setCreateTime(new Date());
            user.setLastLoginTime(new Date());
            User insertUser = userService.insertUser(user);
            if (insertUser == null){
                return TTBFResultUtil.error("注册失败");
            }

            UsernamePasswordToken token = new UsernamePasswordToken(registerVo.getUserName(), registerVo.getPassword(), true);
            //获取当前的Subject
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            //跳转登陆接口
            return TTBFResultUtil.success("登录成功！");
        }catch (Exception e){
            log.error("注册失败", e);
            return TTBFResultUtil.error(e.getMessage());
        }
    }


    /**
     * 退出登陆接口
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void logout() {
    }

    /**
     * 判断是否登陆接口
     */
    @RequestMapping(value = "/islogin",method = RequestMethod.GET)
    public ResponseVO isLogin() {
        Object principals = SecurityUtils.getSubject().getPrincipals();
        if (principals != null){
            return TTBFResultUtil.success("已登陆");
        }else {
            return TTBFResultUtil.error("未登录");
        }
    }
}
