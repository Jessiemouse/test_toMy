package com.example.test.controller;

import com.example.test.entity.User;
import com.example.test.service.AES256Util;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserRegister {

    @Autowired
    private UserService service; //注入服務(實體才需new，其他用注入)

    @GetMapping("/register")//api url
    public String register(Model model){ //註冊的方法，model:html的元素
        User user = new User(); //新建一個實例
        model.addAttribute("user",user);//抓取html的user丟到資料庫的user
        return "newRegister";//回傳

    }
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user){ //傳送html的user的資料丟到後端
        String password = user.getMemPassword();//取出密碼
        String encodePassWord = AES256Util.encode(password);//密碼加密
        user.setMemPassword(encodePassWord);//塞回物件存進資料庫
        service.registerUser(user);//使用了UserService中的registerUser方法
        return "registerSuccess";//回傳字串到thymeleaf>>註冊成功頁面

    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
