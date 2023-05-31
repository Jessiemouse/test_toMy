package com.example.test.register_login.service;

import com.example.test.register_login.entity.User;
import com.example.test.register_login.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
