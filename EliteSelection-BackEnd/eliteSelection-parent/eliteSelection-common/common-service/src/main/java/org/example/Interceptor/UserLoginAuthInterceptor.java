package org.example.Interceptor;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.CustomLoginException;
import org.example.model.entity.user.UserInfo;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userInfoJSON = redisTemplate.opsForValue().get("user:eliteSelection:"+token);
        if(StringUtils.isEmpty(userInfoJSON)) {
            throw new CustomLoginException(ResultCodeEnum.LOGIN_AUTH);
        }
        // 将登录的用户信息放入到ThreadLocal对象中
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJSON, UserInfo.class));
        return true;
    }
}
