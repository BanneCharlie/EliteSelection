package com.banne.service.imp;

import com.banne.mapper.UserAddressMapper;
import com.banne.service.UserAddressService;
import org.example.model.entity.user.UserAddress;
import org.example.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAddressServiceImp implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {
        // 1.获取当前用户的登录id
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findByUserId(userId);
    }
}
