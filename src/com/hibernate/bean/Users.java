package com.hibernate.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name="user_login")
public class Users {
	@Id
	@Column(name="id")
	//@GeneratedValue
	private String id;
	private String username;
	private String password;
	//private String nameConfirmed;
	
	@Autowired
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	@Autowired
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Autowired
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	
	
//	@Transient
//	public void setNameConfirmed(String nameConfirmed) {
//		this.nameConfirmed = nameConfirmed;
//	}
//	
//	public String getNameConfirmed() {
//		return this.nameConfirmed;
//	}
	
	public boolean isNew() {
		return this.id == null || this.id.equals("");
	}
}
