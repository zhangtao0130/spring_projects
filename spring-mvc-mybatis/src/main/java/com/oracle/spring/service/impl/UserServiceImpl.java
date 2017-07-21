package com.oracle.spring.service.impl;

import com.oracle.spring.domain.User;
import com.oracle.spring.mapper.UserMapper;
import com.oracle.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangtao on 7/15/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public void createNewUser(User user) {
        userMapper.createNewUser(user);
    }


}
