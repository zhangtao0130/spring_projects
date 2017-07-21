package com.oracle.spring.service;

import com.oracle.spring.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangtao on 7/15/2017.
 */
public interface UserService {

    List<User> findAllUsers();

    void createNewUser(User user);
}
