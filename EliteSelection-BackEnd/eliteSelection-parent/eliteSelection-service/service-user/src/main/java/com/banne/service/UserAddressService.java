package com.banne.service;

import org.example.model.entity.user.UserAddress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAddressService {
    List<UserAddress> findUserAddressList();
}
