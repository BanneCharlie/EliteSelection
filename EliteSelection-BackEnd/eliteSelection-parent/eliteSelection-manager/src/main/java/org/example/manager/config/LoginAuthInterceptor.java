package org.example.manager.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entity.system.SysUser;
import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

// 拦截器 拦截除登录 注册 验证码图片生成外的其他接口
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AuthContextUtil authContextUtil;

    // 在请求被实际处理之前调用,返回true 则继续执行后续的拦截器和处理器 返回false结束执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //0.预检请求直接放行
        String method = request.getMethod();
        if ("OPTIONS".equals(method)){
            return true;
        }
        //1.获取token 根据token从redis中获取用户信息
        String token = request.getHeader("token");
        String userInfo = redisTemplate.opsForValue().get("user:login:" + token);
        // 将Json格式的字符串userInfo 转换为 SysUser对象
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);
        //2.判断用户信息是否存在
        if (StrUtil.isEmpty(userInfo)) {
            //返回给前端 208状态码 以及用户未登录信息
            responseNoLoginInfo(response);
            return false;
        }
        //3.用户存在将用户信息存放入ThreadLocal中,方便后端服务获取当前登录的用户信息
        authContextUtil.setThreadLocal(sysUser);

        //4.重置Redis中用户数据的有效时间
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);
        return true;
    }
    //用户数据不存在,响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) writer.close();
        }
    }

    // 请求被处理后 视图渲染前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    // 在整个请求结束之后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 将ThreadLocal的用户信息进行删除
        authContextUtil.remove();
    }
}
