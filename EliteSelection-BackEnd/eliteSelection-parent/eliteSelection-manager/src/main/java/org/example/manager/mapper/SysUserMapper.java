package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.model.dto.system.SysUserDto;
import org.example.model.entity.system.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SysUserMapper {

    // 根据用户名查询用户信息
    SysUser selectByUserName(String userName);

    // 查询用户所有信息
    List<SysUser> findByPage(SysUserDto sysUserDto);


    // 添加数据
    void saveSysUser(SysUser sysUser);

    // 修改数据
    void updateSysUser(SysUser sysUser);

    void deleteSysUser(Long userId);
}
