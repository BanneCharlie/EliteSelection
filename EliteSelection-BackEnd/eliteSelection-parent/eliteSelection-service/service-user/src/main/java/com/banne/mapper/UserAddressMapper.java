package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.user.UserAddress;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findByUserId(Long userId);
}
