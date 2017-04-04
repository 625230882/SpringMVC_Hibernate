package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.common.model.UsersPhoto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.bean.Users;
import com.hibernate.bean.UsersInfo;
import com.hibernate.service.UsersManager;
import com.hibernate.validator.UsersInfoValidator;
import com.hibernate.validator.UsersValidator;
import com.springmvc.redis.RedisCache;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	private static final Logger logger = Logger.getLogger(UsersController.class.getName());

	@Autowired
	@Qualifier(value = "UsersManagerImpl")
    private UsersManager usersManager;
	
	@Autowired
	private UsersValidator usersValidator;
	
	@Autowired
	private UsersInfoValidator usersInfoValidator;
	
	@Autowired
	private RedisCache redisCache;
	
	@Autowired
	private ObjectMapper mapper;
	
	@InitBinder("users")
	protected void initUsersBinder(WebDataBinder binder) {
		binder.setValidator(usersValidator);
	}
	
	@InitBinder("usersInfo")
	protected void initUsersInfoBinder(WebDataBinder binder) {
		binder.setValidator(usersInfoValidator);
	}
	
	
	@RequestMapping(value="/")
    public String index(Model model) {
		model.addAttribute("loginForm", new Users());
        return "index";
    }
	
	
	//addUser
    @RequestMapping(value="/userForm", method=RequestMethod.GET)
    public String SayHi(Model model) {
		
        model.addAttribute("users", new Users());
        return "user_form";
    }
    
    
    //addUser
    @RequestMapping(value="/userForm", method=RequestMethod.POST)
    public String userForm(@ModelAttribute("users") @Validated Users user, BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
    	
    	if(result.hasErrors()) {
    		return "user_form";
    	}
    	
    	usersManager.addOrUpdateUser(user);
    	
    	return "redirect:/users/";
    }
    
    
    //show form for user update
    @RequestMapping(value="/{id}/update", method=RequestMethod.GET)
    public String updateUser(@PathVariable("id") String id, Model model) {
    	UsersInfo usersInfo = usersManager.findInfoById(id);
    	
    	try {
			UsersPhoto obj = mapper.readValue(usersInfo.getPhoto(), UsersPhoto.class);
			
			model.addAttribute("image", obj.getPhoto());
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	model.addAttribute("usersInfo", usersInfo);
    	
    	return "user_form";
    }
    
    //update user 
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String updateUser(@ModelAttribute("usersInfo") @Validated UsersInfo usersInfo, BindingResult result, @PathVariable("id") String id, Model model, final RedirectAttributes redirectAttributes
    		,HttpServletRequest req) {
    	
    	if(result.hasErrors()) {
    		return "user_form";
    	}
    	
    	List<String> pathList = new ArrayList<String>();
    	
    	UsersPhoto usersPhoto = new UsersPhoto();
    	
    	for(MultipartFile file: usersInfo.getImages()) {
	    	//add img into folder
	    	File imageFile = new File(req.getServletContext().getRealPath("/images"), file.getOriginalFilename());
	   	    
	    	pathList.add(req.getServletContext().getRealPath("/images") + "/" + file.getOriginalFilename());
	    	
	    	try {
				file.transferTo(imageFile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    	
    	try {
    		usersPhoto.setPhoto(pathList);
    		
    		//convert list to json
			String jsonInString = mapper.writeValueAsString(usersPhoto);
			
			//update photo list
			usersInfo.setPhoto(jsonInString);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	usersManager.updateUserInfo(usersInfo);
    	
    	return "redirect:/users/showAllUsers";
    }
    
    
    //show user information with this id
    @RequestMapping(value="/showAllUsers/{id}", method=RequestMethod.GET)
    public String showUserDetail(@PathVariable("id") String id, Model model) {
    	UsersInfo user = usersManager.findInfoById(id);
    	if(user == null) {
    		model.addAttribute("message", "no such user");
    	}
    	model.addAttribute("user", user);
    	return "user_detail";
    }
    
    
    //show all user
    @RequestMapping(value="/showAllUsers", method=RequestMethod.GET)
    public String showAllUsers(Model model) {
    	List<Users> list = usersManager.getUsers();
    	model.addAttribute("users", list);
    	return "user_list";
    }
    
    
    //login check
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginAuthentication(@ModelAttribute("loginForm")  Users user, Model model, BindingResult result,  final RedirectAttributes redirectAttributes
    		, HttpServletResponse response, HttpServletRequest request) {

    	if(result.hasErrors()) {
    		return "index";
    	}
    	
    	logger.debug((String)("this is message"));
    	boolean confirmed = usersManager.loginCheck(user);
    	
    	if(!confirmed) {
    		model.addAttribute("error", "Username or Password not correct");
    		return "index";
    	}
    	
    	String token = UUID.randomUUID().toString();
    	
    	//add cookie to response
    	Cookie cookie = new Cookie("authentication-token", token);
    	cookie.setHttpOnly(true);
    	response.addCookie(cookie);
    	
    	HttpSession session = request.getSession(false);
    	if(session!=null) {
    		//set expire time
    		session.setMaxInactiveInterval(110);
    		
    		//put token into session in redis
    		session.setAttribute(token, usersManager.findByName(user.getUsername()).getId());
    	}

    	return "redirect:/users/showAllUsers";
    }
    
    
    //logout
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logOut(@CookieValue("authentication-token") String token, Model model, HttpServletResponse response, final RedirectAttributes redirectAttributes,
    		HttpServletRequest request) {
    	
    	usersManager.logOut(request.getSession().getId());
    	
    	HttpSession session = request.getSession(false);
    	if(session!=null)
    		request.getSession().invalidate();
    	
    	Cookie cookie = new Cookie("authentication-token", null); // Not necessary, but saves bandwidth.
    	cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
    	cookie.setHttpOnly(true);
    	response.addCookie(cookie);
    	
    	return "redirect:/users/";
    }
}