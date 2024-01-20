package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.model.entity.system.SysUser;

@Mapper
public interface SysUserMapper {

    // 根据用户名查询用户信息
    @Select("select * from sys_user where username = #{userName} and is_deleted = 0")
    SysUser selectByUserName(String userName);
}
