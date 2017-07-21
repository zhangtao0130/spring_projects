package com.oracle.spring.service.impl;

import com.oracle.spring.dao.UserDAO;
import com.oracle.spring.domain.User;
import com.oracle.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangtao on 7/14/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }
}
