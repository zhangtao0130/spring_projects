package com.oracle.spring.mapper;

import com.oracle.spring.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangtao on 7/17/2017.
 */
@Component
public interface UserMapper {

    List<User> findAllUsers();

    void createNewUser(User user);
}
