package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo repo; //注入repo(串接資料庫用)
    @Override
    public void registerUser(User user) {//繼承了介面所以實作方法
        repo.save(user);
    } //實作註冊方法的內容:存在repo裡


}
