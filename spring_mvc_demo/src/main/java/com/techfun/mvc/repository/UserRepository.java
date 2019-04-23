package com.techfun.mvc.repository;

import com.techfun.mvc.model.Login;
import com.techfun.mvc.model.User;

public interface UserRepository {

	void registerUser(User user);
	
	User validateUser(Login login);
	
}
