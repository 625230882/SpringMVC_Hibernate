package com.hibernate.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.antlr.runtime.debug.DebugEventHub;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.redis.RedisCache;

public class LoginInterceptor implements HandlerInterceptor{

	private final String loginUrl = "/users/";
	private final String showUsersUrl = "/users/showAllUsers";
	private static final Logger logger = Logger.getLogger(LoginInterceptor.class.getName());

	@Autowired
	private RedisCache redisCache;
	
	@Override  
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {  
 
        String url = req.getContextPath();

//        String token = req.getHeader("Authentication-token");
//        if(token == null || token.equals(""))
//        	res.sendRedirect(url + loginUrl);  
        showAllHeader(req);
        
        String requestURI = req.getRequestURI().toString().substring(url.length(), req.getRequestURI().toString().length());
        
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie=cookies[i];
                String cookieName = cookie.getName();
                
                logger.debug(cookies.toString());
                if(cookieName.equals("authentication-token")) {
                	//get token from cookie
                	String token = cookie.getValue();
                                	
                	//use token to check whether user has login
                	String userId = (String)req.getSession().getAttribute(token);
                	                	
                	//intercept access if user not login
                    if (userId == null || "".equals(userId.toString())) {  
                    	//no use login and access login page
                    	
                    	if(requestURI != null && requestURI.equals("/users/")) {
                    		//can't access user info after log out
             
                    		return true;
                    	}
                        res.sendRedirect(url + loginUrl);  
                        return false;
                    }  
                    
                    //intercept user login if user already login
                    if(requestURI != null && requestURI.equals("/users/")) {
                    	res.sendRedirect(url + showUsersUrl);
                    	return false;
                    }
                    
                    return true;
                }
            }
        }
        
        if(requestURI != null && requestURI.equals("/users/")) 
    		return true;
        
        res.sendRedirect(url + loginUrl);  
        return false;  
    }  
  
	
    @Override  
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {  
    } 
    
    
    public void showAllHeader(HttpServletRequest req) {
    	Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
        	String key = (String) headerNames.nextElement();
        	String val = req.getHeader(key);
        	map.put(key, val);
        }
        for(Entry<String, String> entry: map.entrySet()) 
        	logger.debug(entry.getKey() + ",  " + entry.getValue());
    }
}
