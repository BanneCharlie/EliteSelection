package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {

    void deleteByUserId(Long userId);

    void doAssign(@Param("roleId")Long roleId, Long userId);

    List<Long> findByUserId(Long userId);
}
