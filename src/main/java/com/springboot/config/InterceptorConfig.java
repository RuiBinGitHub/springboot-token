package com.springboot.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.bean.MyInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Resource
	private MyInterceptor myInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有请求，通过判断是否有@LoginRequired 注解决定是否需要登录
		registry.addInterceptor(myInterceptor).addPathPatterns("/**");
	}
}
