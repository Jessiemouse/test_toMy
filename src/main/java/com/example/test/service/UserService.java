package com.example.test.service;

import com.example.test.entity.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {//若有人繼承這個介面，就必須實作這個介面裡所有的方法
    public void registerUser(User user);//介面中的註冊方法，需在Impl裡實作


}
