package com.springboot.biz.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.entity.User;

@Service("TokenService")
public class TokenService {

	public String getToken(User user) {
		Date start = new Date();
		long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;// 一小时有效时间
		Date end = new Date(currentTime);
		String token = "";
		String[] ids = new String[] { user.getId() + "" };
		token = JWT.create().withAudience(ids).withIssuedAt(start).withExpiresAt(end)
				.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
}
