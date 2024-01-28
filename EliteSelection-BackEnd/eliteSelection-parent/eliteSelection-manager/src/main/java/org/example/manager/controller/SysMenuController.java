package org.example.manager.controller;

import org.example.manager.service.SysMenuService;
import org.example.model.entity.system.SysMenu;
import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    /**
     * 菜单列表
     * @return
     */
    @GetMapping("/sysMenu/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @PostMapping("/sysMenu/save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @PutMapping("/sysMenu/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/sysMenu/removeById/{id}")
    public Result removeById(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 查询所有的菜单并且根据角色Id找到当前角色选中的菜单
     * @param id
     * @return
     */
    @GetMapping(value = "/sysRoleMenu/findSysRoleMenuByRoleId/{roleId}")
    public Result<Map<String , Object>> findSysRoleMenuByRoleId(@PathVariable(value = "roleId") Long roleId) {
        Map<String , Object> sysRoleMenuList = sysMenuService.findSysRoleMenuByRoleId(roleId) ;
        return Result.build(sysRoleMenuList , ResultCodeEnum.SUCCESS) ;
    }

    // 为当前角色分配菜单

}
