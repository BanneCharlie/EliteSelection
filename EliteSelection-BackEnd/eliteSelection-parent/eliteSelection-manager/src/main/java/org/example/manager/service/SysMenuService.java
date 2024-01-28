package org.example.manager.service;

import org.example.model.entity.system.SysMenu;
import org.example.model.vo.system.SysMenuVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    List<SysMenuVo> findUserMenuList();
}
