package org.example.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.manager.service.SysMenuService;
import org.example.manager.service.SysUserService;
import org.example.manager.service.ValidateCodeService;
import org.example.model.dto.system.LoginDto;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.system.LoginVo;
import org.example.model.vo.system.SysMenuVo;
import org.example.model.vo.system.ValidateCodeVo;
import org.example.utils.AuthContextUtil;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/index")
@Tag("用户接口")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private AuthContextUtil authContextUtil;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 生成验证码
     * @return validateCodevo(key,value)
     */
    @Operation(summary = "验证码接口")
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS);
    }
    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取当前登录用户信息
     * @param token
     * @return
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo(){
        SysUser sysUser = authContextUtil.getThreadLocal();
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户退出
     * @param token
     * @return
     */
    @Operation(summary = "用户退出")
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 后端管理系统前台展示系统管理的权限展示
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }

}
