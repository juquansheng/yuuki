package com.malaxiaoyugan.yuukiadmin.filter;

import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Slf4j
public class LoginAuthorizationFilter extends FormAuthenticationFilter {
    /**
     * 这个方法是未登录需要执行的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            //设置响应头
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json");
            //设置返回的数据
            ResponseVO result = TTBFResultUtil.unLogin("用户未登录");
            //写回给客户端
            PrintWriter out = httpResponse.getWriter();
            out.write(JSONObject.toJSONString(result));
            //刷新和关闭输出流
            out.flush();
            out.close();
        } else {
            //设置响应头
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json");
            //设置返回的数据
            ResponseVO result = TTBFResultUtil.unAuth("用户未登录");
            //写回给客户端
            PrintWriter out = httpResponse.getWriter();
            out.write(JSONObject.toJSONString(result));
            //刷新和关闭输出流
            out.flush();
            out.close();
        }
        return false;
    }

}
