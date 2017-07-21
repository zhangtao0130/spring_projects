package com.oracle.spring.web.controller;

import com.oracle.spring.domain.Task;
import com.oracle.spring.domain.User;
import com.oracle.spring.service.TaskService;
import com.oracle.spring.service.UserService;
import com.oracle.spring.web.validator.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangtao on 7/12/2017.
 */
@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private static final int[] priorities = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    //tasks?status=Open
    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public String list(@RequestParam(name = "status", required = false) String status, Model model) {
        model.addAttribute("status", status);
        if (StringUtils.isEmpty(status)) {
            model.addAttribute("tasks", taskService.findAllTasks());
            model.addAttribute("status", "All");
        } else if (status.equals("Open")) {
            model.addAttribute("tasks", taskService.findAllOpenTasks());
        } else if (status.equals("Closed")) {
            model.addAttribute("tasks", taskService.findAllClosedTasks());
        } else {
            throw new IllegalArgumentException("");
        }
        return "task/list";
    }

    //新建task
    @RequestMapping(path = "/tasks/new", method = RequestMethod.GET)
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        //使用@ModelAttribute代替
        // model.addAttribute("priorities", priorities);
        // model.addAttribute("users", userService.findAllUsers());
        return "task/new";
    }

    @RequestMapping(path = "/tasks/new", method = RequestMethod.POST)
    public String createNewTask(@ModelAttribute("task") @Valid Task task, BindingResult result, Model model) {
        //System.out.println(task.getAssignee().getId() + "-----------------");
        // System.out.println(task.getCreatedBy().getId() + "-----------------");
        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            //logger.error(">>>>>>> Validation ERROR : " + result.getAllErrors());
            return "task/new";
        } else {
            taskService.createTask(task);
            return "redirect:/tasks";
        }
    }

    //查看任务
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.GET)
    public ModelAndView viewTask(@PathVariable("id") Long id) {
        //  return new ModelAndView("task/view").addObject("task", taskService.findTaskById(id)).addObject("priorities", priorities);
        return new ModelAndView("task/view").addObject("task", taskService.findTaskById(id));
    }

    //修改
    @RequestMapping(path = "/tasks/{id}/edit", method = RequestMethod.GET)
    public String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findTaskById(id));
        model.addAttribute("priorities", priorities);
        model.addAttribute("users", userService.findAllUsers());
        return "task/edit";
    }

    //修改保存
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.PUT)
    public String saveTask(@ModelAttribute("task") @Valid Task task, BindingResult result, Model model) {
        // task.setAssignee(userService.findById(task.getAssignee().getId()));
        // task.setCreatedBy(userService.findById(task.getCreatedBy().getId()));
        Task existingTask = taskService.findTaskById(task.getId());
        existingTask.setAssignee(task.getAssignee());
        existingTask.setName(task.getName());
        existingTask.setPriority(task.getPriority());
        existingTask.setComments(task.getComments());

        return "redirect:/tasks/" + task.getId();
    }

    //更改任务状态为Closed
    @RequestMapping(path = "/tasks/{id}/complete", method = RequestMethod.PUT)
    public String completeTask(@PathVariable("id") Long id, @ModelAttribute("task") @Valid Task task,
                               BindingResult result, Model model) {
        // taskService.completeTask(task);
        Task existingTask = taskService.findTaskById(task.getId());

        existingTask.setComments(task.getComments());
        existingTask.setStatus("Closed");
        existingTask.setCompletedDate(new Date());

        return "redirect:/tasks/" + id;
    }

    //删除
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.DELETE)
    public String deleteTask(@PathVariable("id") Long id, Model model) {

        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    //返回数据类型为
    @RequestMapping(path = "/tasks.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Task> listTasksJson(@RequestParam(name = "status", required = false) String status) {
       // logger.info(">>>>>>>>>>>>>>>>>> inside " + this + ".listTasksJson() >>>>>>>>>>>> Status = " + status);
        if (StringUtils.isEmpty(status)) {
            return taskService.findAllTasks();
        } else if (status.equals("Open"))
            return taskService.findAllOpenTasks();
        else if (status.equals("Closed"))
            return taskService.findAllClosedTasks();
        else
            throw new IllegalArgumentException(
                    "Illegal value for status supplied: " + status + "; Only 'Open' and 'Closed' are supported.");
    }


    //优先权
    @ModelAttribute("priorities")
    public int[] getTaskPriorities() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    //用户信息
    @ModelAttribute("users")
    public List<User> getUsersList() {
        return userService.findAllUsers();
    }

    //绑定验证类
    @InitBinder("task")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new TaskValidator());
    }

}
