package com.banne.controller;

import com.banne.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.example.model.dto.h5.UserLoginDto;
import org.example.model.dto.h5.UserRegisterDto;
import org.example.model.entity.user.UserInfo;
import org.example.model.vo.common.*;
import org.example.model.vo.h5.UserInfoVo;
import org.example.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("ClientUserController")
@RequestMapping("/api/user/userInfo")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS) ;
    }

    // 返回当前登录的用户信息
    @GetMapping("/auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        // 通过 ThreadLocal进行获取
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return Result.build(userInfoVo , ResultCodeEnum.SUCCESS) ;
    }
}
