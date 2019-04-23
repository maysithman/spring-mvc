package com.techfun.mvc.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.techfun.mvc.model.User;
import com.techfun.mvc.service.UserService;

public class AppUser {

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService staffService = appContext.getBean("userService", UserService.class);
		
		testInsertUser(staffService);
	}

	private static void testInsertUser(UserService staffService) {
		User user = new User();
		user.setUsername("May Sit Hman");
		user.setPassword("123456");
		user.setFirstname("May");
		user.setLastname("Sit Hman");
		user.setEmail("maysismhan@gmail.com");
		user.setAddress("Yagon");
		user.setPhone(33333);
		
		staffService.registerUser(user);
		System.out.println("Insert User Successfully.");
	}

}
