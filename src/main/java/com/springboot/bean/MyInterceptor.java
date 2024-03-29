package com.springboot.bean;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springboot.biz.UserBiz;
import com.springboot.entity.User;

@Component
public class MyInterceptor implements HandlerInterceptor {

	@Resource
	private UserBiz userBiz;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String token = request.getHeader("token");// 从 http 请求头中取出
		System.out.println(token);
		// 如果不是映射到方法直接通过
		if (!(object instanceof HandlerMethod)) {
			return true;
		}
		Method method = ((HandlerMethod) object).getMethod();
		
		// 检查是否有passtoken注释，有则跳过认证
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		// 检查有没有需要用户权限的注解
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
			if (userLoginToken.required()) {
				// 执行认证
				if (token == null) {
					throw new RuntimeException("无token，请重新登录");
				}
				// 获取 token 中的 user id
				String userId;
				try {
					userId = JWT.decode(token).getAudience().get(0);
				} catch (JWTDecodeException j) {
					throw new RuntimeException("401");
				}
				User user = userBiz.findInfoUser(Integer.valueOf(userId));
				if (user == null) {
					throw new RuntimeException("用户不存在，请重新登录");
				}
				// 验证 token
				JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
				try {
					jwtVerifier.verify(token);
				} catch (JWTVerificationException e) {
					throw new RuntimeException("401");
				}
				return true;
			}
		}
		return true;
	}

	// @Override
	// public void postHandle(HttpServletRequest request, HttpServletResponse
	// response, Object object,
	// ModelAndView modelAndView) throws Exception {
	//
	// }
	//
	// @Override
	// public void afterCompletion(HttpServletRequest request,
	// HttpServletResponse response,
	// Object o, Exception e) throws Exception {
	//
	// }
}
