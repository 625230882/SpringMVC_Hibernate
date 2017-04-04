package com.hibernate.dao;

import java.util.List;

import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;

public interface UsersDao {
	
	public void save(Users user);
	
	public void update(Users user);
	
	public void update(UsersInfo user);
	
	public List<Users> list();
	
	public Users findById(String id);
	
	public UsersInfo findInfoById(String id);
	
	public Users findByName(String name);
	
}
