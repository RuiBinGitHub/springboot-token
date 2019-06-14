package com.springboot.biz;


import com.springboot.entity.User;

public interface UserBiz {
	
	public User findInfoUser(int id);
	
	public boolean login(String username, String password);
	
}
