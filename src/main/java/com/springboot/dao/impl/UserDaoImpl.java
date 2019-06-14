package com.springboot.dao.impl;

import org.springframework.stereotype.Component;

import com.springboot.dao.UserDao;
import com.springboot.entity.User;

@Component
public class UserDaoImpl implements UserDao {

	public User findInfoUser(int id) {
		User user = null;
		if (id == 1) {
			user = new User();
			user.setId(id);
			user.setUsername("100001");
			user.setPassword("123456");
			user.setDate("2012-05-06");
		}
		if (id == 2) {
			user = new User();
			user.setId(id);
			user.setUsername("100002");
			user.setPassword("123456");
			user.setDate("2012-05-06");
		}
		return user;
	}

	public User login(String username, String password) {
		User user = null;
		if ("100001".equals(username) && "123456".equals(password)) {
			user = new User();
			user.setId(1);
			user.setUsername(username);
			user.setPassword(password);
			user.setDate("2012-05-06");
		}
		if ("100002".equals(username) && "123456".equals(password)) {
			user = new User();
			user.setId(2);
			user.setUsername(username);
			user.setPassword(password);
			user.setDate("2012-05-06");
		}
		return user;
	}
}
