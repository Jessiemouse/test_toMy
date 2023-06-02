package com.example.test.register_login.service;

import com.example.test.register_login.UserNotFoundException;
import com.example.test.register_login.entity.User;

public interface UserService {//若有人繼承這個介面，就必須實作這個介面裡所有的方法
    public void registerUser(User user);//介面中的註冊方法，需在Impl裡實作
    public boolean checkMemAccount(String memAccount);
    public boolean checkMemEmail(String memEmail);
    public  void updateResetPasswordToken(String token,String memEmail) throws UserNotFoundException;
    public User get(String resetPasswordToken);
    public void updatePassword(User user, String newPassword);
}
