package com.example.test.register_login.controller;

import com.example.test.register_login.UserNotFoundException;
import com.example.test.register_login.entity.User;
import com.example.test.register_login.repo.UserRepo;
import com.example.test.register_login.service.UserService;
import com.example.test.register_login.util.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
@SessionAttributes("user")
public class UserForgotPw {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model) {
        model.addAttribute("pageTitle", "忘記密碼");
        return "forgot_pw";
    }

    @ModelAttribute("user")
    public User getUserModelAttribute() {
        return new User();
    }

    @PostMapping("/forgotpassword")
    public String processForgotPassword(@ModelAttribute User user, HttpSession session, HttpServletRequest request, Model model) {
        String memEmail = request.getParameter("memEmail");
        String token = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        System.out.println("Email: " + memEmail);
        System.out.println("Token: " + token);
        try {

            userService.updateResetPasswordToken(token, memEmail);
            String resetPaswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPaswordLink);
            sendEmail(memEmail, resetPaswordLink);
            model.addAttribute("error", "重設密碼信已寄出!");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", "查無此Email，請重新輸入");

        } catch (MessagingException e) {
            model.addAttribute("error", "傳送失敗");
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("error", "傳送失敗");
        }
        return "forgot_pw";
    }

    private void sendEmail(String memEmail, String resetPaswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom("TeacherWanted123@gmail.com", "Teacher Wanted");
        helper.setTo(memEmail);
        String subject = "請重新設定密碼";
        String content = "<p>親愛的用戶您好，<p>"
                + "<p>您已申請重新設定密碼，<p>"
                + "<p><b><a href=\"" + resetPaswordLink + "\">請點擊此連結前往重設密碼</a><b><p>"
                + "<p><p>"
                + "<p>懸賞啼雀團隊 敬上<p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title", "重設密碼");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "重設密碼");
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title", "重設密碼");
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else{
            userService.updatePassword(user, password);
            model.addAttribute("message","設定成功");
        }
        return "login";
    }

}
