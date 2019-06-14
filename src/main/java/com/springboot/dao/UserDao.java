package com.springboot.dao;

import com.springboot.entity.User;

public interface UserDao {

	public User findInfoUser(int id);

	public User login(String username, String password);
}
