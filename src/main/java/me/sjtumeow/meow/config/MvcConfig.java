package me.sjtumeow.meow.config;

import java.util.List;
import me.sjtumeow.meow.authorization.interceptor.AuthorizationInterceptor;
import me.sjtumeow.meow.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类，增加自定义拦截器和解析器
 * 
 * @see me.sjtumeow.meow.authorization.resolvers.CurrentUserMethodArgumentResolver
 * @see me.sjtumeow.meow.authorization.interceptor.AuthorizationInterceptor
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }
}
