package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.dto.h5.UserRegisterDto;
import org.example.model.entity.user.UserInfo;

@Mapper
public interface UserMapper {
    void save(UserInfo userInfo);

    UserInfo getByUserName(String username);
}
