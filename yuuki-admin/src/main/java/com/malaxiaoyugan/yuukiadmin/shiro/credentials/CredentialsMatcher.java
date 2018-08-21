package com.malaxiaoyugan.yuukiadmin.shiro.credentials;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.service.UserService;
import com.malaxiaoyugan.yuukicore.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Shiro-密码凭证匹配器（验证密码有效性）
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Resource
    private UserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
        Object principals = info.getPrincipals();
        long l = Long.parseLong(principals.toString());
        User userById = userService.getUserById(Long.parseLong(principals.toString()));
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        try {
            inPassword = PasswordUtil.encrypt(inPassword, userById.getHash());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}
