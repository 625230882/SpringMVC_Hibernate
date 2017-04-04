package com.hibernate.dao;


import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;

@Repository
public class UsersDaoImpl implements UsersDao {


	private SessionFactory sessionFactory;

	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
	public void save(Users p) {
		UsersInfo usersInfo = new UsersInfo();
		String id = UUID.randomUUID().toString();
		p.setId(id);
		usersInfo.setId(id);
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(p);
		session.persist(usersInfo);
		tx.commit();
		session.close();
	}
	
	public void update(Users user) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Users set username=:username where id=:id");
		query.setParameter("id", user.getId());
		query.setParameter("username", user.getUsername());
		Transaction tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.close();
	}
	
	public void update(UsersInfo userInfo) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update UsersInfo set email=:email, name=:name, birthdate=:birthdate, address=:address, phone=:phone, photo=:photo, sex=:sex where id=:id ");
		query.setParameter("id", userInfo.getId());
		query.setParameter("email", userInfo.getEmail());
		query.setParameter("name", userInfo.getName());
		query.setParameter("birthdate", userInfo.getBirthdate());
		query.setParameter("address", userInfo.getAddress());
		query.setParameter("phone", userInfo.getPhone());
		query.setParameter("sex", userInfo.getSex());
		query.setParameter("photo", userInfo.getPhoto());
		Transaction tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> list() {
		Session session = this.sessionFactory.openSession();
		List<Users> personList = session.createQuery("from Users").getResultList();
		session.close();
		return personList;
	}
	
	
	public Users findById(String id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Users where id=:id");
		query.setParameter("id", id);
		Users user = (Users)query.getSingleResult();
		session.close();
		return user;
	}
	
	public UsersInfo findInfoById(String id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from UsersInfo where id=:id");
		query.setParameter("id", id);
		UsersInfo user = (UsersInfo)query.getSingleResult();
		session.close();
		return user;
	}
	
	public Users findByName(String name) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Users where username=:username");
		query.setParameter("username", name);
		List<Users> list = query.getResultList();
		Users user = null;
		if(list.isEmpty())
			return user;
		else
			user = list.get(0);
		session.close();
		return user;
	}
	

}

