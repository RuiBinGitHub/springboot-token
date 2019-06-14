package com.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bean.UserLoginToken;
import com.springboot.biz.UserBiz;
import com.springboot.biz.impl.TokenService;
import com.springboot.entity.User;
import com.springboot.util.TokenUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserBiz userBiz;
	@Resource
	private TokenService tokenService;

	@GetMapping("/login")
	public String login(String username, String password,HttpServletResponse  response) {
		User userForBase =userBiz.findInfoUser(1);

		if (userForBase == null) {
			return "登录失败,密码错误";
		} else {
			String token = tokenService.getToken(userForBase);
			Cookie cookie = new Cookie("token", token);
			cookie.setPath("/");
			response.addCookie(cookie);
			return token;
		}
	}

	@UserLoginToken
	@GetMapping("/getMessage")
	public String getMessage() {
		// 取出token中带的用户id 进行操作
		System.out.println(TokenUtil.getTokenUserId());
		return "你已通过验证";
	}
}
