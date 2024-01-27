package org.example.manager.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.example.manager.service.SysRoleService;
import org.example.model.dto.system.SysRoleDto;
import org.example.model.entity.system.SysRole;
import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysRole")
@Tag("角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色的信息
     * @param pageNum
     * @param pageSize
     * @param sysRoleDto
     * @return
     */
    @Operation(summary = "查询角色信息")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@PathVariable("pageNum") int pageNum,
                             @PathVariable("pageSize") int pageSize,
                             @RequestBody SysRoleDto sysRoleDto){
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage (pageNum,pageSize,sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色信息
     * @param SysRole
     * @return
     */
    @Operation(summary = "添加角色信息")
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改角色信息
     * @param sysRole
     * @return
     */
    @Operation(summary = "修改角色信息")
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @Operation(summary = "删除角色信息")
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteSysRole(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteSysRole(roleId) ;
        return Result.build(null, ResultCodeEnum.SUCCESS) ;
    }
}
