package com.hibernate.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hibernate.bean.Users;

@Component
public class UsersValidator implements Validator{

	
	@Override
	public boolean supports(Class<?> clazz) {
		return Users.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.User.Username");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.User.Email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.User.Password");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameConfirmed", "NotEmpty.User.Name");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.User.Name");
	}
}
