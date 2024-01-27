package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.model.entity.system.SysUser;
import org.springframework.stereotype.Repository;

@Mapper
public interface SysUserMapper {

    // 根据用户名查询用户信息
    SysUser selectByUserName(String userName);
}
