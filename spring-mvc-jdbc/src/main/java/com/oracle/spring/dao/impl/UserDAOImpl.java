package com.oracle.spring.dao.impl;

import com.oracle.spring.dao.UserDAO;
import com.oracle.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhangtao on 7/14/2017.
 */
@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAllUsers() {
        String sql = "select * from TBL_USER";
       // return jdbcTemplate.queryForList(sql,null,User.class);
        List<User> userList = jdbcTemplate.query(sql, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("NAME"));
            user.setUserName(resultSet.getString("USER_NAME"));
            user.setPassword(resultSet.getString("PASSWORD"));
            user.setDateOfBirth(resultSet.getDate("DOB"));
            return user;
        });
        return userList;
    }
}
