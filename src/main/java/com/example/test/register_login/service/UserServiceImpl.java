package com.example.test.register_login.service;

import com.example.test.register_login.UserNotFoundException;
import com.example.test.register_login.entity.User;
import com.example.test.register_login.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo repo; //注入repo(串接資料庫用)
    @Override
    public void registerUser(User user) {//繼承了介面所以實作方法
        repo.save(user);
    } //實作註冊方法的內容:存在repo裡

    @Override
    public boolean checkMemAccount(String memAccount) {
        return repo.existsByMemAccount(memAccount);
    }
    @Override
    public boolean checkMemEmail(String memEmail) {
        return repo.existsByMemEmail(memEmail);
    }
    @Override
    public  void updateResetPasswordToken(String token,String memEmail) throws UserNotFoundException {
        User user = repo.findByMemEmail(memEmail);
        if(user != null){
            user.setResetPasswordToken(token);
            repo.save(user);
        } else {
            throw new UserNotFoundException("查無此Email"+memEmail);
        }
    }

    public User get(String resetPasswordToken){
        return repo.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword){
        String newpassword = user.getResetPasswordToken();//取出密碼
        String encodePassword = AES256Util.encode(newpassword);//密碼加密
        user.setMemPassword(encodePassword);//塞回物件存進資料庫
        user.setResetPasswordToken(null);

        repo.save(user);
    }


}
