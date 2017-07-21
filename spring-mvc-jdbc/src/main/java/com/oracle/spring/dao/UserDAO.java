package com.oracle.spring.dao;

import com.oracle.spring.domain.User;

import java.util.List;

/**
 * Created by zhangtao on 7/14/2017.
 */
public interface UserDAO {

    List<User> findAllUsers();

}
