package com.oracle.dao;

import com.oracle.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by zhangtao on 7/2/2017.
 */
public interface TaskDAO extends JpaRepository<Task, Long>{

   @Query("select count(t) from Task t where status = 'Complete'")
   int findAllCompletedTasksCount();

}
