package org.example.manager.service.imp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.level.DebugLog;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.CustomLoginException;
import org.example.manager.mapper.SysRoleUserMapper;
import org.example.manager.mapper.SysUserMapper;
import org.example.manager.service.SysUserService;
import org.example.model.dto.system.AssginRoleDto;
import org.example.model.dto.system.LoginDto;
import org.example.model.dto.system.SysUserDto;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SysUserServiceImp implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 实现用户的登录
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 0.获取提交的验证码 和 验证码的key值
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();

        String key = "user:login:validatecode:" + codeKey;
        // 获取redis中的验证码
        String redisCode = (String) redisTemplate.opsForValue().get("user:login:validatecode:"+codeKey);

        // 判断redis中的验证码是否正确 或者 验证码为空
        if (!captcha.equals(redisCode) || StrUtil.isEmpty(captcha)) {
            // 自定义异常,进行抛出
            throw new CustomLoginException(ResultCodeEnum.CAPTCHA_ERROR);
        }

        // 1.获取提交的用户 获取用户名
        String userName = loginDto.getUserName();
        // 2.根据用户名查询表 sys_user
        SysUser sysUser = sysUserMapper.selectByUserName(userName);

        // 3.查询不到对应的信息,用户不存在 返回错误信息
        if (sysUser  ==  null){
            // 自定义异常,进行抛出
            throw new CustomLoginException(ResultCodeEnum.USER_NOT_EXIST);
        }
        // 4.查询到用户信息,用户存在 获取输入的密码与数据库是否一致
        String password = loginDto.getPassword();
        // 对用户输入的密码进行md5加密
        String md5DigestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        // 5.密码一致登录成功 不同登录失败
        if (!md5DigestPassword.equals(sysUser.getPassword())){
            // 自定义异常,进行抛出
            throw new CustomLoginException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 6.登录成功,生成用户的唯一标识token  --> 通过UUID来进行生成
        String token = UUID.randomUUID().toString().replace("-", "");

        // 7.登录成功将用户信息存放入redis中, token为key 用户信息为value
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(sysUser),2, TimeUnit.MINUTES);

        // 8.返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }


    // 用户退出根据token从redis中删除数据
    @Override
    public void logout(String token) {
        String key = "user:login" + token;
        redisTemplate.opsForValue().decrement(key);
    }

    // 实现用户的查询
    @Override
    public PageInfo<SysUser> findByPage(int pageNum, int pageSize, SysUserDto sysUserDto) {
        // 1.初始化需要分页的数据
        PageHelper.startPage(pageNum, pageSize);
        // 2.执行查询
        List<SysUser> lists = sysUserMapper.findByPage(sysUserDto);
        // 3.返回当前页的数据
        return new PageInfo<SysUser>(lists);
    }

    // 用户的添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        // 1.根据用户名称判断当前用户是否存在
        if (sysUserMapper.selectByUserName(sysUser.getUserName()) != null) {
            // 抛出自定义异常
            throw new CustomLoginException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 2.将用户输入的密码进行加密处理
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        // 3.设置用户状态为1
        sysUser.setStatus(1);

        sysUserMapper.saveSysUser(sysUser);
    }

    // 实现用户的修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    // 实现用户的删除
    @Override
    public void deleteSysUser(Long userId) {
        sysUserMapper.deleteSysUser(userId);
    }

    @Override
    public void doAssgin(AssginRoleDto assginRoleDto) {
        // 1.根据用户id删除原有的角色
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        // 2.根据用户id添加上新的角色
        for(Long roleId : assginRoleDto.getRoleIdList()){
            sysRoleUserMapper.doAssign(roleId, assginRoleDto.getUserId());
        }
    }
}
