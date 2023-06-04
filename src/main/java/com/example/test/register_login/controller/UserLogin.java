package com.example.test.register_login.controller;


import com.example.test.register_login.entity.User;
import com.example.test.register_login.repo.UserRepo;
import com.example.test.register_login.service.AES256Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@SessionAttributes("user")
public class UserLogin {
    @Autowired(required = true)
    private UserRepo repo;//注入repo(要從資料庫撈資料)


    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user"); // 移除会话中的用户信息
        }

        User user = new User();
        model.addAttribute("user", user);

        // 将会话属性设置为模型属性
        model.addAttribute("sessionUser", session.getAttribute("user"));

        return "login";
    }


    @PostMapping("/userLogin")
    public String userLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        String xxx = user.getMemAccount();
        Optional<User> userdata = repo.findByMemAccount(xxx);
        String password = AES256Util.decode(userdata.get().getMemPassword());//解密
        if (userdata.isPresent() && user.getMemPassword().equals(password)) {
            System.out.println(userdata.get());
            // 在登入成功後設置 Session
            session.setAttribute("user", userdata.get());
            model.addAttribute("memName", userdata.get().getMemName());
            model.addAttribute("memNickname", userdata.get().getMemNickname());
            session.setAttribute("memAccount", userdata.get().getMemAccount());
            model.addAttribute("msg", "登入成功，٩(◕‿◕｡)۶歡迎回來~ " + userdata.get().getMemNickname());


            return "index";
        } else {
            return "loginFail";
        }
    }


    @GetMapping("/logout")
    public String logout(@ModelAttribute("user")User user, HttpSession session, Model model) {
        // 清除会话中的用户信息
        session.removeAttribute("user");
        session.invalidate();
        model.addAttribute("msg", "您已登出，( •́ω•̩̥̀ )下次再會~");
        System.out.println(user.getMemAccount()+" already logout");
        return "index";
    }




    @GetMapping("/index")
    public String index(Model model, HttpSession session) {

        return "index";
    }




}