package com.malaxiaoyugan.yuukiadmin.shiro;

import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.holder.SpringContextHolder;
import com.malaxiaoyugan.yuukicore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro-权限相关的业务处理
 */
@Slf4j
@Service
public class ShiroService {


    @Autowired
    private UserService userService;

    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        /*
            配置访问权限
            - anon:所有url都都可以匿名访问
            - authc: 需要认证才能进行访问（此处指所有非匿名的路径都需要登陆才能访问）
            - user:配置记住我或认证通过可以访问
            - restFilter自定义过滤器设置跨域
            - token自定义登陆过滤器
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/passport/logout", "restFilter,TTBFLogout");
        filterChainDefinitionMap.put("/passport/login", "restFilter,anon");
        filterChainDefinitionMap.put("/passport/register", "restFilter,anon");
        filterChainDefinitionMap.put("/favicon.ico", "restFilter,anon");
        filterChainDefinitionMap.put("/error", "restFilter,anon");
        filterChainDefinitionMap.put("/assets/**", "restFilter,anon");
        filterChainDefinitionMap.put("/plugin/**", "restFilter,anon");
        filterChainDefinitionMap.put("/vendors/**", "restFilter,anon");
        //文件上传获取
        filterChainDefinitionMap.put("/file/**", "sessionFilter,anon");
        //验证码
        filterChainDefinitionMap.put("/code/**", "restFilter,anon");
        //图片验证码
        filterChainDefinitionMap.put("/verifyImage", "anon");
        //首页
        filterChainDefinitionMap.put("/index/**", "restFilter,anon");
        //个人中心
        filterChainDefinitionMap.put("/profile/**", "sessionFilter,anon");
        //文章修改和添加
        filterChainDefinitionMap.put("/article/edit", "sessionFilter,token");
        //文章接口
        filterChainDefinitionMap.put("/article/getdetail", "anon");
        filterChainDefinitionMap.put("/article/getlist", "anon");
        filterChainDefinitionMap.put("/article/isbelong", "anon");
        //评论接口
        filterChainDefinitionMap.put("/comment/insert", "restFilter,token");
        filterChainDefinitionMap.put("/comment/delete", "restFilter,token");
        filterChainDefinitionMap.put("/comment/getlist", "restFilter,anon");
        //回复接口
        filterChainDefinitionMap.put("/reply/**", "sessionFilter,anon");

        filterChainDefinitionMap.put("/demo/**", "restFilter,anon");
        filterChainDefinitionMap.put("/file/**", "restFilter,anon");
        // 加载数据库中配置的资源权限列表
       /* List<Resources> resourcesList = resourcesService.listUrlAndPermission();
        for (Resources resources : resourcesList) {
            if (!StringUtils.isEmpty(resources.getUrl()) && !StringUtils.isEmpty(resources.getPermission())) {
                String permission = "perms[" + resources.getPermission() + "]";
                filterChainDefinitionMap.put(resources.getUrl(), permission);
            }
        }*/
        // 本博客中并不存在什么特别关键的操作，所以直接使用user认证。如果有朋友是参考本博客的shiro开发其他安全功能（比如支付等）时，建议针对这类操作使用authc权限
        filterChainDefinitionMap.put("/**", "restFilter,token");
        return filterChainDefinitionMap;
    }

    /**
     * 重新加载权限
     */
    public void updatePermission() {
        ShiroFilterFactoryBean shirFilter = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        synchronized (shirFilter) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shirFilter.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shirFilter.getFilterChainDefinitionMap().clear();
            shirFilter.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shirFilter.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }

    /**
     * 重新加载用户权限
     *
     * @param user
     */
    public void reloadAuthorizingByUserId(User user) {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) rsm.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(user.getId(), realmName);
        subject.runAs(principals);
        shiroRealm.getAuthorizationCache().remove(subject.getPrincipals());
        subject.releaseRunAs();

        log.info("用户[{}]的权限更新成功！！", user.getUserName());

    }

    /**
     * 重新加载所有拥有roleId角色的用户的权限
     *
     * @param roleId
     */
    public void reloadAuthorizingByRoleId(Long roleId) {
        List<User> userList = userService.getUserByRoleId(roleId);
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        for (User user : userList) {
            reloadAuthorizingByUserId(user);
        }
    }

}
