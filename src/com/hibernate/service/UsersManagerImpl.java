package com.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;
import com.hibernate.dao.UsersDao;
import com.springmvc.redis.RedisCache;

@Component(value = "UsersManagerImpl")
@Service
public class UsersManagerImpl implements UsersManager{

	@Autowired
	UsersDao usersDao;
	
	@Autowired
	RedisCache redisCache;
	
	public List<Users> getUsers() {
		return usersDao.list();
	}
	
	public void addOrUpdateUser(Users user) {
		if(user.isNew())
			usersDao.save(user);
		else
			usersDao.update(user);
	}
	
	public void updateUserInfo(UsersInfo userInfo) {
		usersDao.update(userInfo);
	}

	
	public Users findById(String id) {
		return usersDao.findById(id);
	}
	
	public UsersInfo findInfoById(String id) {
		return usersDao.findInfoById(id);
	}
	
	public Users findByName(String name) {
		return usersDao.findByName(name);
	}
	
	public boolean loginCheck(Users user) {
		if(user.getUsername().equals("") || user.getUsername() == null || user.getPassword().equals("") || user.getPassword() == null)
			return false;
		Users correctUser = usersDao.findByName(user.getUsername());
		if(correctUser == null || !correctUser.getPassword().equals(user.getPassword()))
			return false;
		
		return true;
	}
	
	public void logOut(String sessionId) {
		redisCache.deleteKey(sessionId);
	}
}
