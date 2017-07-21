package com.oracle.spring.boot;

import com.oracle.spring.domain.User;
import com.oracle.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;


@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextRefreshedListener.class);

    @Autowired
    private UserService userService;

//    @Autowired
//    private TaskService taskService;

    private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/servlet-uploads";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info(">>>>>>>>>>>>>>>>>>> Inside " + this + ".onApplicationEvent--- <<<<<<<<<<");

        User shameer = new User(new Long(0), "Shameer Kunjumohamed", "sameerean", "password", new Date());
        userService.createNewUser(shameer);
        discoverProfileImageIfExists(shameer);

        User tarun = new User(new Long(1), "Tarun Bhati", "tbhati", "password", new Date());
        userService.createNewUser(tarun);
        discoverProfileImageIfExists(tarun);

//        taskService.createTask(new Task("Order Food", 10, "Open", shameer, new Date(), tarun, "This is for breakfast"));
//        taskService.createTask(new Task("Commit code changes", 5, "Open", shameer, new Date(), tarun, "Let's test and deploy on Staging Server"));
//        taskService.createTask(new Task("Review code changes", 6, "Open", tarun, new Date(), shameer, "Please send your feedback"));
//        taskService.createTask(new Task("Release project version", 3, "Open", tarun, new Date(), shameer, "All tested, verified, accepted"));
//        taskService.createTask(new Task("Order Snacks", 9, "Closed", tarun, new Date(), shameer, "Order some samosas and juices from Haji Ali"));
    }

    private void discoverProfileImageIfExists(User user) {
        String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
        File physicalDir = new File(
                fileSaveDirectory);

        if (physicalDir.exists()) {
            File[] files = physicalDir.listFiles();
            if (files != null && files.length > 0) {
                File firstFile = files[0];
                user.setProfileImage(new com.oracle.spring.domain.File(1000 + user.getId() + 1, firstFile.getName()));
            }
        }
    }

}
