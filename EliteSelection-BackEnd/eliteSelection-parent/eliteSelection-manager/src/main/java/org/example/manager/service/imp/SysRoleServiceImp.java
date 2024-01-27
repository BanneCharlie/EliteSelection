package org.example.manager.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.mapper.SysRoleMapper;
import org.example.manager.service.SysRoleService;
import org.example.model.dto.system.SysRoleDto;
import org.example.model.entity.system.SysRole;
import org.example.model.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImp implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(int pageNum, int pageSize, SysRoleDto sysRoleDto) {
        //1.根据传输的pageNum 和 pageSize初始化
        PageHelper.startPage(pageNum, pageSize);
        //2.根据传输的sysRoleDto进行查询
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        //3.将查询到的sysRoleList 转换为PageInfo
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void deleteSysRole(Long roleId) {
        sysRoleMapper.deleteSysRole(roleId);
    }
}
