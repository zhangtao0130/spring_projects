package com.oracle.spring.dao;

import com.oracle.spring.domain.File;
import com.oracle.spring.domain.User;

import java.util.List;


public interface UserDAO {
	
	void createUser(User user);
	User findById(Long userId);
	User findByUserName(String userName);
	void deleteUser(User user);
	List<User> findAllUsers();

	File addProfileImage(Long userId, String fileName);
	void removeProfileImage(Long userId);
}
