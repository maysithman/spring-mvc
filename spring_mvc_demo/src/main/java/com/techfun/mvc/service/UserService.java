package com.techfun.mvc.service;

import com.techfun.mvc.model.Login;
import com.techfun.mvc.model.User;

public interface UserService {

	void registerUser(User user);
	
	User validateUser(Login login);
	
}