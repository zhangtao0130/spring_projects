package com.oracle.spring.service;

import com.oracle.spring.domain.User;

import java.util.List;

/**
 * Created by zhangtao on 7/14/2017.
 */
public interface UserService {

    List<User> findAllUsers();
}
