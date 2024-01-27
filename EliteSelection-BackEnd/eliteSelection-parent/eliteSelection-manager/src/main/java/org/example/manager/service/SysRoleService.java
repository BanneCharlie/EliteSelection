package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import org.example.model.dto.system.SysRoleDto;
import org.example.model.entity.system.SysRole;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SysRoleService {
    PageInfo<SysRole> findByPage(int pageNum, int pageSize, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRole(Long roleId);

    Map<String, Object> findAllRoles(Long userId);
}
