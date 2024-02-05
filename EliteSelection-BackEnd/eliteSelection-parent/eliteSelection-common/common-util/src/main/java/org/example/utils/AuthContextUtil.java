package org.example.utils;

import org.example.model.entity.system.SysUser;
import org.example.model.entity.user.UserInfo;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterJob;

// AuthContextUtil 工具类的作用就是将当前用户存放在ThreadLocal中,方便读取;
@Component
public class AuthContextUtil {
    // 创建一个ThreadLocal对象 存储SysUser对象
    public static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<SysUser>();

    // 存储数据的静态方法
    public static void setThreadLocal(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 获取数据的静态方法
    public static SysUser getThreadLocal() {
        return threadLocal.get();
    }
    // 删除数据
    public static void remove() {
        threadLocal.remove();
    }

    // 创建一个ThreadLocal对象 存储User对象
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }

}
