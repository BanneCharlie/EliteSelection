package com.banne.service;

import org.example.model.dto.h5.UserLoginDto;
import org.example.model.dto.h5.UserRegisterDto;
import org.example.model.vo.h5.UserInfoVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
