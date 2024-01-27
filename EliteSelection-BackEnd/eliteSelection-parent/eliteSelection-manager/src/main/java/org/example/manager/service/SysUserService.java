package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import org.example.model.dto.system.AssginRoleDto;
import org.example.model.dto.system.LoginDto;
import org.example.model.dto.system.SysUserDto;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.system.LoginVo;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {
    LoginVo login(LoginDto loginDto);
    
    void logout(String token);

    PageInfo<SysUser> findByPage(int pageNum, int pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteSysUser(Long userId);

    void doAssgin(AssginRoleDto assginRoleDto);
}
