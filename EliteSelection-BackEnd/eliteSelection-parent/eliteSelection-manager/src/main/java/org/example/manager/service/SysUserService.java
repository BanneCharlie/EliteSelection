package org.example.manager.service;

import org.example.model.dto.system.LoginDto;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.system.LoginVo;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {
    LoginVo login(LoginDto loginDto);


    void logout(String token);
}
