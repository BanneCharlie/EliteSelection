package org.example.manager.controller;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Update;
import org.example.manager.service.SysUserService;
import org.example.model.dto.system.AssginRoleDto;
import org.example.model.dto.system.SysUserDto;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.model.vo.common.Result;

@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 实现用户的查询(实现分页)
     * @param pageNum
     * @param pageSize
     * @param sysUserDto
     * @return
     */
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findByPage(@PathVariable("pageNum") int pageNum,
                                                @PathVariable("pageSize") int pageSize,
                                                SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 实现用户的添加
     * @param sysUser
     * @return
     */
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 实现用户的修改
     * @param sysUser
     * @return
     */
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除用户信息
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteSysUser(@PathVariable("userId") Long userId) {
        sysUserService.deleteSysUser(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //为用户分配角色
    @PostMapping("/doAssign")
    public Result doAssgin(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssgin(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
