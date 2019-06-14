package com.springboot.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.UserBiz;
import com.springboot.dao.UserDao;
import com.springboot.entity.User;

@Service
public class UserBizImpl implements UserBiz {

	@Resource
	private UserDao userDao;

	public User findInfoUser(int id) {
		return userDao.findInfoUser(id);
	}

	public boolean login(String username, String password) {
		if (userDao.login(username, password) == null)
			return false;
		return true;
	}

}
