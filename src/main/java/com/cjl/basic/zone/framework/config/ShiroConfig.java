package com.cjl.basic.zone.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.cjl.basic.zone.framework.shiro.cache.ShiroRedisCacheManager;
import com.cjl.basic.zone.framework.shiro.jwt.StatelessToken;
import com.cjl.basic.zone.framework.shiro.jwt.filter.StatelessFilter;
import com.cjl.basic.zone.framework.shiro.jwt.subject.StatelessWebSubjectFactory;
import com.cjl.basic.zone.framework.shiro.realm.StatelessRealm;
import com.cjl.basic.zone.framework.shiro.web.filter.StatelessLogoutFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 *
 * @author chen
 * 不要使用以下代码，它会导致service层的aop失效
 * public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
 * DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
 * proxyCreator.setProxyTargetClass(true);
 * return proxyCreator;
 * }
 */
@Configuration
public class ShiroConfig {

    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/adminTpl/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/utils/**", "anon");
        filterChainDefinitionMap.put("/spider/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/monitor/druid*/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginzg", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/statement", "anon");
        // 系统权限列表
//        filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("stateless", new StatelessFilter());
        filters.put("statelessLogout", new StatelessLogoutFilter());
        filters.put("perms", new StatelessFilter());

        shiroFilterFactoryBean.setFilters(filters);

        filterChainDefinitionMap.put("/logout", "statelessLogout");
        // 所有请求需要认证
        filterChainDefinitionMap.put("/**", "stateless");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(StatelessRealm statelessRealm, StatelessWebSubjectFactory subjectFactory, ShiroRedisCacheManager shiroRedisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        /*关闭redis session*/
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        /*自定义subjectFactory*/
        securityManager.setSubjectFactory(subjectFactory);

        /*配置Realm*/
        securityManager.setRealm(statelessRealm);

        // 设置缓存
        securityManager.setCacheManager(shiroRedisCacheManager);

        return securityManager;
    }
//
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(RedisTemplate redisTemplate, @Value("${application.cache.prefix}") String prefix) {
        return new ShiroRedisCacheManager(redisTemplate, prefix);
    }

    /**
     * 不创建session
     */
    @Bean
    StatelessWebSubjectFactory subjectFactory() {
        return new StatelessWebSubjectFactory();
    }

    /**
     * 自定义Realm
     */
    @Bean
    public StatelessRealm realm() {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setCachingEnabled(true);
        statelessRealm.setAuthenticationCachingEnabled(true);
        statelessRealm.setAuthenticationTokenClass(StatelessToken.class);
        statelessRealm.setAuthorizationCachingEnabled(true);
        return statelessRealm;
    }

    @Bean
    ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @author: tianyong
     * @time: 2018/8/14 17:09
     * @description:开启代码权限注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
