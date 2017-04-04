package com.hibernate.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;
import com.hibernate.interceptor.LoginInterceptor;

@Component
public class UsersInfoValidator implements Validator{

	private static final Logger logger = Logger.getLogger(UsersInfoValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsersInfo.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		logger.debug("start in validate");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.User.Username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.User.Email");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.User.Password");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameConfirmed", "NotEmpty.User.Name");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.User.Name");
	}
}
