package com.malaxiaoyugan.yuukicore.interceptor;

import com.malaxiaoyugan.yuukicore.constants.SessionConst;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RememberAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return true;
        }
        Session session = subject.getSession(true);
        if (session.getAttribute(SessionConst.USER_SESSION_KEY) != null) {
            return true;
        }
        if (!subject.isRemembered()) {
            log.warn("未设置“记住我”,跳转到登录页...");
            response.sendRedirect(request.getContextPath() + "/passport/login");
            return false;
        }
        try {
            Long userId = Long.parseLong(subject.getPrincipal().toString());
            User user = userService.getUserById(userId);
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), PasswordUtil.decrypt(user.getPassword(), user.getUserName()), true);
            subject.login(token);
            session.setAttribute(SessionConst.USER_SESSION_KEY, user);
            log.info("[{}] - 已自动登录", user.getUserName());
        } catch (Exception e) {
            log.error("自动登录失败", e);
            response.sendRedirect(request.getContextPath() + "/passport/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
