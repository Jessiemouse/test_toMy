package com.example.test.controller;


import com.example.test.entity.User;
import com.example.test.repo.UserRepo;
import com.example.test.service.AES256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.beans.BeanProperty;
import java.beans.beancontext.BeanContext;
import java.util.Optional;

@Controller
public class UserLogin {
    @Autowired(required = true)
    private UserRepo repo;//注入repo(要從資料庫撈資料)

    @GetMapping("/login")//api url
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute("user") User user,Model model) {//取得html的user
        String xxx = user.getMemAccount(); //從html的user中取出MemAccount
        Optional<User> userdata = repo.findByMemAccount(xxx);//從資料庫中去找user中的MemAccount
        String password = AES256Util.decode(userdata.get().getMemPassword());//解密
        if (userdata.isPresent() && user.getMemPassword().equals(password)) {//如果userdata存在並且資料庫裡的密碼和html輸入的密碼符合

            model.addAttribute("memName", userdata.get().getMemName()); // 將memName添加到模型中
            return "LoginSuccess";
        } else {
            return "LoginFail";
        }
    }
//    @GetMapping("/session")
//    public ResponseEntity<User> getSessionInfo(@SessionAttribute("user") User user){
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}