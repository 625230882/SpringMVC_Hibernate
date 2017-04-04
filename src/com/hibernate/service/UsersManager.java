package com.hibernate.service;

import java.util.List;

import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;

public interface UsersManager {

	public void addOrUpdateUser(Users user);
	
	public void updateUserInfo(UsersInfo userInfo);
	
	public List<Users> getUsers();
	
	public Users findById(String id);
	
	public UsersInfo findInfoById(String id);
	
	public Users findByName(String name);
	
	public boolean loginCheck(Users user);
	
	public void logOut(String token);
}
