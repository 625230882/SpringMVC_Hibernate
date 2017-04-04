package com.hibernate.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="user_info")
public class UsersInfo {

	@Id
	@Column(name="id")
	//@GeneratedValue
	private String id;
	private String email;
	private String name;
	private String birthdate;
	private String address;
	private String phone;
	private String sex;
	private String photo;
	
	@Transient
	private List<MultipartFile> images;
	//private String nameConfirmed;
	
	@Autowired
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	
	
	@Autowired
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	
	@Autowired
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	@Autowired
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getBirthdate() {
		return this.birthdate;
	}
	
	@Autowired
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}
	
	@Autowired
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return this.phone;
	}
	
	@Autowired
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSex() {
		return this.sex;
	}
	
	@Autowired
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhoto() {
		return this.photo;
	}
	
	
	//	@Transient
	//	public void setNameConfirmed(String nameConfirmed) {
	//		this.nameConfirmed = nameConfirmed;
	//	}
	//	
	//	public String getNameConfirmed() {
	//		return this.nameConfirmed;
	//	}
	
	public List<MultipartFile> getImages() {
        return images;
    }
	
	
    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
	
	public boolean isNew() {
		return this.id == null || this.id.equals("");
	}
	
}
