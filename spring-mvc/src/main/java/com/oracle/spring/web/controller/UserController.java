package com.oracle.spring.web.controller;

import com.oracle.spring.domain.User;
import com.oracle.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangtao on 7/11/2017.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/servlet-uploads";

    @Autowired
    private UserService userService;

    //查询所有用户信息
    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Locale locale, Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    //新增用户信息
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newUserForm(Model model) {
        User user = new User();
        user.setDateOfBirth(new Date());
        model.addAttribute("user", user);
        return "user/new";
    }

    //保存用户信息
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/users";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewUser(@PathVariable("id") Long id) {
        return new ModelAndView("user/view").addObject("user", userService.findById(id));
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/edit";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("user", userService.findById(id));
        return "redirect:/users/" + id;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User existingUser = userService.findById(id);
        userService.deleteUser(existingUser);
        return "redirect:/users";
    }

    //展示上传页面
    @RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.GET)
    public ModelAndView uploadProfileImage(@PathVariable("userId") Long userId) {
        return new ModelAndView("user/upload").addObject("user", userService.findById(userId));
    }

    //处理上传信息
    @RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.POST)
    public String uploadProfileImage(@PathVariable("userId") Long userId, @RequestParam("profileImage") MultipartFile file) throws IOException {
        User user = userService.findById(userId);
        //创建文件路径
        String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
        //判断上传文件是否为空
        if (!file.isEmpty()) {
            java.io.File fileDir = new java.io.File(fileSaveDirectory); //创建路径
            if (!fileDir.exists()) {
                fileDir.mkdirs(); //创建文件夹
            }
            //拷贝文件放入服务器中
            FileCopyUtils.copy(file.getBytes(), new java.io.File(fileSaveDirectory + "/" + file.getOriginalFilename()));
            //
            this.userService.addProfileImage(userId, file.getOriginalFilename());
        }
        return "redirect:/users/" + userId;
    }

    //显示图片
    @RequestMapping(path = "/{userId}/profileImage", method = RequestMethod.GET)
    public void downloadProfileImage(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {
        User user = userService.findById(userId);
        if (user.getProfileImage() != null) {
            String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
            //设置file
            java.io.File physicalFile = new java.io.File(fileSaveDirectory + "/" + user.getProfileImage().getFileName());
            if (physicalFile.exists()) {
                //根据文件的名称猜测文件的类型
                String mimeType = URLConnection.guessContentTypeFromName(physicalFile.getName());
                //设置响应头 --- 表示后面的文档属于什么MIME类型
                response.setContentType(mimeType);
                System.out.println(String.format("inline: filename=\"" + physicalFile.getName() + "\"") + "--------");
                //Content-Disposition 属性是作为对下载文件的一个标识字段
                //inline 和 attachment  inline ：将文件内容直接显示在页面
                //设置响应头 ---
                response.setHeader("Content-Disposition",
                        String.format("inline; filename=\"" + physicalFile.getName() + "\""));
                //设置响应头 --- 表示内容长度
                response.setContentLength((int) physicalFile.length());
                //设置文件输入流
                InputStream inputStream = new BufferedInputStream(new FileInputStream(physicalFile));
                //将输入流的信息copy到输出流中
                FileCopyUtils.copy(inputStream, response.getOutputStream());
                return;
            }
        }
        //错误文字提示信息
        String errorMessage = "Sorry. The file you are looking for does not exist";
        System.out.println(errorMessage);
        OutputStream outputStream = response.getOutputStream();
        //输出错误提示信息
        outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
        //关闭输出流
        outputStream.close();
    }
}


