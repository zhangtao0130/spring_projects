package com.oracle.service;

/**
 * Created by zhangtao on 7/2/2017.
 */
public interface TaskService {

    /**
     *
     * @return
     */
    int findAllTasksCount();

    /**
     *
     * @return
     */
    int findAllOpenTasksCount();

}
