package com.oracle.spring.web.controller;

import com.oracle.spring.domain.Task;
import com.oracle.spring.domain.User;
import com.oracle.spring.service.TaskService;
import com.oracle.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;

/**
 * Created by zhangtao on 7/10/2017.
 */
@Controller
public class HomeController {

    //private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        // logger.info("Welcome to Taskify! The client local is {},", locale);
        model.addAttribute("totalTasks", taskService.findAllTasksCount());
        model.addAttribute("totalOpenTasks", taskService.findAllOpenTasksCount());
        return "home";
    }

}
