package com.example.sharedbike.shiro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import com.example.sharedbike.jwt.JwtFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

	/**
	 * 交由 Spring 来自动地管理 Shiro-Bean 的生命周期
	 */
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 为 Spring-Bean 开启对 Shiro 注解的支持
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
		app.setProxyTargetClass(true);
		return app;

	}

	/**
	 * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
	 * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求 
	 * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
	 */
	@Bean
	public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
		registration.setEnabled(false);
		return registration;
	}
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("sha-1");
		matcher.setHashIterations(16);
		matcher.setStoredCredentialsHexEncoded(true);
		return matcher;
	}
	/**
	 * ShiroRealm 配置，需实现 Realm 接口
	 */
	@Bean
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		//设置shiroRealm的密码验证的匹配器
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return shiroRealm;
	}
	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter();
	}

	/**
	 * 配置访问资源需要的权限
	 */
	@Bean
	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/authorized");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

		// 添加 jwt 专用过滤器，拦截除 /login 和 /logout 外的请求
		Map<String, Filter> filterMap = new LinkedHashMap<>();
		filterMap.put("jwtFilter", jwtFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/login", "anon"); // 可匿名访问
		filterChainDefinitionMap.put("/logout", "logout"); // 退出登录
		filterChainDefinitionMap.put("/swagger-ui.html", "anon"); // 可匿名访问
		filterChainDefinitionMap.put("/swagger-resources/**", "anon"); // Swagger资源
		filterChainDefinitionMap.put("/webjars/**", "anon"); // Swagger相关静态资源
		filterChainDefinitionMap.put("/v2/api-docs", "anon"); // Swagger API文档
		filterChainDefinitionMap.put("/v3/api-docs", "anon"); // Swagger API文档
		filterChainDefinitionMap.put("/swagger-ui/**", "anon"); // Swagger UI
		filterChainDefinitionMap.put("/**", "jwtFilter,authc"); // 需登录才能访问
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置 ModularRealmAuthenticator
	 */
	@Bean
	public ModularRealmAuthenticator authenticator() {
		ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
		// 设置多 Realm的认证策略，默认 AtLeastOneSuccessfulStrategy
		AuthenticationStrategy strategy = new FirstSuccessfulStrategy();
		authenticator.setAuthenticationStrategy(strategy);
		return authenticator;
	}

	/**
	 * 禁用session, 不保存用户登录状态。保证每次请求都重新认证
	 */
	@Bean
	protected SessionStorageEvaluator sessionStorageEvaluator() {
		DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);
		return sessionStorageEvaluator;
	}

	/**
	 * JwtRealm 配置，需实现 Realm 接口
	 */
	@Bean
	JwtRealm jwtRealm() {
		JwtRealm jwtRealm = new JwtRealm();
//		// 设置加密算法
		CredentialsMatcher credentialsMatcher = new JwtCredentialsMatcher();
//		// 设置加密次数
		jwtRealm.setCredentialsMatcher(credentialsMatcher);
		return jwtRealm;
	}




	/**
	 * 配置 SecurityManager
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

		// 1.Authenticator
		securityManager.setAuthenticator(authenticator());

		// 2.Realm
		List<Realm> realms = new ArrayList<Realm>(16);
		realms.add(jwtRealm());
		realms.add(shiroRealm());
		securityManager.setRealms(realms);

		// 3.关闭shiro自带的session
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
		securityManager.setSubjectDAO(subjectDAO);

		return securityManager;
	}
}
