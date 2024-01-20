package org.example.utils;

import org.example.model.entity.system.SysUser;
import org.springframework.stereotype.Component;

// AuthContextUtil 工具类的作用就是将当前用户存放在ThreadLocal中,方便读取;
@Component
public class AuthContextUtil {
    // 创建一个ThreadLocal对象
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
}
