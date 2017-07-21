package com.oracle.service.impl;

import com.oracle.dao.TaskDAO;
import com.oracle.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangtao on 7/2/2017.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public int findAllTasksCount() {
        return (int)taskDAO.count();
    }

    @Override
    public int findAllOpenTasksCount() {
        return taskDAO.findAllCompletedTasksCount();
    }
}
