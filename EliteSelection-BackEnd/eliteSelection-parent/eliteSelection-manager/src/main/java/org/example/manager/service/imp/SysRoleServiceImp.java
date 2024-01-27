package org.example.manager.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.mapper.SysRoleMapper;
import org.example.manager.mapper.SysRoleUserMapper;
import org.example.manager.service.SysRoleService;
import org.example.model.dto.system.SysRoleDto;
import org.example.model.entity.system.SysRole;
import org.example.model.entity.system.SysRoleUser;
import org.example.model.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysRoleServiceImp implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;


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

    /**
     * 添加角色
     * @param sysRole
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    /**
     * 修改角色
     * @param sysRole
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    public void deleteSysRole(Long roleId) {
        sysRoleMapper.deleteSysRole(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        // 1.获取所有的角色列表信息
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();

        // 2.根据用户id获取当前用户对用的角色Id
        List<Long> sysRoleId = sysRoleUserMapper.findByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList", sysRoleList);

        map.put("sysUserRoles", sysRoleId);
        return map;
    }
}
