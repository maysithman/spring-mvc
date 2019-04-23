package com.techfun.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techfun.mvc.model.Login;
import com.techfun.mvc.model.User;
import com.techfun.mvc.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void registerUser(User user) {
		userRepository.registerUser(user);
	}

	@Override
	public User validateUser(Login login) {
		return userRepository.validateUser(login);
	}

}
