package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.UserInfo;
import com.repository.UserInfoRepository;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String addUser(UserInfo userInfo) {
		String email = userInfo.getEmail();
		UserInfo obj1 = repository.findByEmail(email).orElse(null);
		System.out.println(obj1);
		if (obj1 == null) {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			repository.save(userInfo);
			return "Registration Successfully ";
		} else {
			return "This Email is Already Registered.";
		}
	}

	public String getRoles(String username) {
		UserInfo obj2 = repository.findByEmail(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}
}
