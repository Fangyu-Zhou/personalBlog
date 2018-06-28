package com.rain.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*加了Configuration注解才能使spring把该类当成网络配置文件，拦截器才会生效*/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*添加拦截器的使用规范*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")  /*表示admin路径后的所有情况*/
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login"); /*排除登陆和返回登陆界面*/
    }
}
