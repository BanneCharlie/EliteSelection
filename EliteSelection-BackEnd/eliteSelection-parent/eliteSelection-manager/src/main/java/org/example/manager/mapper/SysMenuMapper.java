package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.system.SysMenu;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void deleteById(Long id);

    int countByParentId(Long id);

    List<SysMenu> findUserMenuByUserId(Long userId);

    SysMenu selectByParentId(Long parentId);
}
