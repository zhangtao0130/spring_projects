package com.oracle.spring.web;

import com.oracle.spring.domain.User;
import com.oracle.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Locale;


/**
 * Created by zhangtao on 7/11/2017.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    //图片保存的路径
   // private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/servlet-uploads";

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Model model, Locale locale) {
        String country = locale.getDisplayCountry();
        System.out.println(country + "-----------------");
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newUserForm(Model model) {
        User user = new User();
        user.setDateOfBirth(new Date());
        model.addAttribute("user", user);
        return "user/new";
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/users";
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ModelAndView viewUser(@PathVariable("id") Long id) {
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("user", userService.findById(id));
//        mv.setViewName("user/view");
//        return mv;
//    }
//
//    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        return "user/edit";
//    }
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
//    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
//        userService.updateUser(user);
//        return "redirect:/users/" + id;
//    }
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
//    public String deleteUser(@PathVariable("id") Long id) {
//        User existingUser = userService.findById(id);
//        userService.deleteUser(existingUser);
//        return "redirect:/users";
//    }
//
//    //上传准备
//    @RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.GET)
//    public ModelAndView uploadProfileImage(@PathVariable("userId") Long userId) {
//        return new ModelAndView("user/upload").addObject("user", userService.findById(userId));
//    }
//
//    //上传处理
//    @RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.POST)
//    public String uploadProfileImage(@PathVariable("userId") Long userId, @RequestParam("profileImage") MultipartFile file) throws IOException {
//
//        User user = userService.findById(userId);
//        //图片路径
//        String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
//        if (!file.isEmpty()) { //上传的文件域不为空
//            //创建保存的文件夹
//            File fileDir = new File(fileSaveDirectory);
//            if (!fileDir.exists()) { //如果文件夹不存在
//                fileDir.mkdirs();//创建文件夹
//            }
//            //tmp/servlet-uploads/1/xxx.jpg
//            FileCopyUtils.copy(file.getBytes(), new File(fileSaveDirectory + "/" + file.getOriginalFilename()));
//            System.out.println(file.getOriginalFilename() + "----------");
//            this.userService.addProfileImage(userId, file.getOriginalFilename());
//        }
//        return "redirect:/users/" + userId;
//    }
//
//    //显示图片的请求
//    @RequestMapping(path = "/{userId}/profileImage", method = RequestMethod.GET)
//    public void downloadProfileImage(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {
//        //获取用户信息
//        User user = userService.findById(userId);
//        if (user.getProfileImage() != null) {
//            //图片路径 --- 文件夹路径
//            String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + userId;
//            //图片文件 --- 文件路径
//            File physicalFile = new File(fileSaveDirectory + "/" + user.getProfileImage().getFileName());
//            if (physicalFile.exists()) { //存在
//                System.out.println(physicalFile.getName() + "----------");
//                //根据文件名称获取文件类型
//                String mimeType = URLConnection.guessContentTypeFromName(physicalFile.getName());
//                //设置类型
//                response.setContentType(mimeType);//
//                //将文件内容直接显示在页面
//                response.setHeader("Content-Disposition","inline;filename=" + physicalFile.getName());
//                //response.setHeader("Content-Disposition", String.format("inline;filename=" + physicalFile.getName()));
//                //设置文件的大小
//                response.setContentLength((int)physicalFile.length());
//                 //创建输入流，将图片从硬盘读取到内存中
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(physicalFile));
//                //将输入的信息复制到输出流中
//                FileCopyUtils.copy(inputStream,response.getOutputStream());
//            }
//
//        }
    //   }


}
