package com.banne.service.imp;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.banne.mapper.UserMapper;
import com.banne.service.UserService;
import org.example.exception.CustomLoginException;
import org.example.model.dto.h5.UserLoginDto;
import org.example.model.dto.h5.UserRegisterDto;
import org.example.model.entity.user.UserInfo;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.h5.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        // 0.确保输入的数据全部存在
        if(userRegisterDto.getUsername() == null || userRegisterDto.getPassword() == null || userRegisterDto.getCode() == null){
            throw new CustomLoginException(ResultCodeEnum.DATA_ERROR);
        }

        // 1.从redis中获取验证码信息,对比验证码输入的是否正确
        String code  = redisTemplate.opsForValue().get("phone:code:" + userRegisterDto.getUsername());

        // 判断验证码输入是否正确
        if(!code.equals(userRegisterDto.getCode())){
            throw new CustomLoginException(ResultCodeEnum.CAPTCHA_ERROR);
        }
        // 2.判断当前用户是否存在
        UserInfo userInfo = userMapper.getByUserName(userRegisterDto.getUsername());
        if (userInfo != null){
            throw new CustomLoginException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        // 3.将输入的密码进行md5加密
        String password = DigestUtil.md5Hex(userRegisterDto.getPassword());
        userRegisterDto.setPassword(password);

        // 5.保存用户的信息,将用户信息存放入数据库中
        UserInfo newUserInfo = new UserInfo();
        newUserInfo.setUsername(userRegisterDto.getUsername());
        newUserInfo.setPassword(userRegisterDto.getPassword());
        newUserInfo.setNickName(userRegisterDto.getUsername());
        newUserInfo.setPhone(userRegisterDto.getUsername());
        newUserInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        newUserInfo.setStatus(1);
        newUserInfo.setSex(0);

        userMapper.save(newUserInfo);

        // 5.删除redis中的验证码,释放数据
        redisTemplate.delete("phone:code:" + userRegisterDto.getUsername());
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        // 1.获取登录的用户名称和密码
        String password = userLoginDto.getPassword();
        String username = userLoginDto.getUsername();

        // 判断用户和密码是否为空
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new CustomLoginException(ResultCodeEnum.DATA_ERROR);
        }

        // 2.通过用户的名称,从数据库中获取数据
        UserInfo userInfo = userMapper.getByUserName(username);
        if (userInfo == null){
            throw new CustomLoginException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 3.判断用户的密码是否正确
        if (!userInfo.getPassword().equals(DigestUtil.md5Hex(password))){
            throw new CustomLoginException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 4.校验用户是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new CustomLoginException(ResultCodeEnum.ACCOUNT_STOP);
        }

        // 4.将用户信息存放到redis中
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:eliteSelection:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);

        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        String userInfoJSON = redisTemplate.opsForValue().get("user:eliteSelection:" + token);
        if(StringUtils.isEmpty(userInfoJSON)) {
            throw new CustomLoginException(ResultCodeEnum.LOGIN_AUTH) ;
        }
        UserInfo userInfo = JSON.parseObject(userInfoJSON , UserInfo.class) ;
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo ;
    }
}
