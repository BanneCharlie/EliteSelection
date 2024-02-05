package org.example.filter;

import cn.hutool.core.text.AntPathMatcher;
import com.alibaba.excel.event.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.model.entity.user.UserInfo;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Order {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public int order() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取访问的IP路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        log.info("path {}", path);
        // 2.通过token获取登录的用户
        UserInfo userInfo = this.getUserInfo(request);

        // 3.api接口，异步请求，校验用户必须登录
        if(antPathMatcher.match("/api/**/auth/**", path)) {
            if(null == userInfo) {
                ServerHttpResponse response = exchange.getResponse();
                // 用户未登录,返回报错信息
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        // 4.用户登录实现放行
        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        if(!StringUtils.isEmpty(token)) {
            String userInfoJSON = redisTemplate.opsForValue().get("user:eliteSelection:"+token);
            if(StringUtils.isEmpty(userInfoJSON)) {
                return null ;
            }else {
                return JSON.parseObject(userInfoJSON , UserInfo.class) ;
            }
        }
        return null;
    }
}
