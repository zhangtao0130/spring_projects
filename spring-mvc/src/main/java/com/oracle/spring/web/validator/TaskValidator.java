package com.oracle.spring.web.validator;

import com.oracle.spring.domain.Task;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by zhangtao on 7/13/2017.
 */
@Component
public class TaskValidator implements Validator {

    //指定校验的对象类型
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Task.class);
    }

    //对目标类型进行校验
    @Override
    public void validate(Object target, Errors errors) {

        Task task = (Task) target;

        //手动校验
//        if("".equals(task.getName()) || task.getName() == null ){
//            errors.rejectValue("name","error.required",new Object[]{"Task name"}, "error....");
//           // errors.rejectValue("name", "error.required", "Task name");
//        }

        //使用校验器工具类进行校验
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "error.required", new Object[]{"Task name"});

        ValidationUtils.rejectIfEmpty(errors, "createdBy.id",
                "error.required", new Object[]{"Created user"});

        ValidationUtils.rejectIfEmpty(errors, "assignee.id",
                "error.required", new Object[]{"Assigned user"});

        ValidationUtils.rejectIfEmpty(errors, "priority",
                "error.required", new Object[]{"Priority"});

        //

    }
}
