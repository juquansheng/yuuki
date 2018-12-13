package com.malaxiaoyugan.yuukiadmin.filter;

import com.malaxiaoyugan.yuukicore.aop.TTBFException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SessionFilter implements Filter {
    private long serverSessionTimeout = 18000000L;//ms


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = null;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        }
        HttpServletResponse res = null;
        if (response instanceof HttpServletResponse) {
            res = (HttpServletResponse) response;
        }
        if (req != null && res != null) {
            //设置允许传递的参数
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            //设置允许带上cookie
            res.setHeader("Access-Control-Allow-Credentials", "true");
            String origin = Optional.ofNullable(req.getHeader("Origin")).orElse(req.getHeader("Referer"));
            //设置允许的请求来源
            res.setHeader("Access-Control-Allow-Origin", origin);
            //设置允许的请求方法
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        }
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.getSession().setTimeout(serverSessionTimeout);
        }catch (Exception e){
            throw new TTBFException(401,"登录超时请重新登录");
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
