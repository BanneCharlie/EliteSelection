package org.example.manager.service.imp;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import org.example.manager.service.ValidateCodeService;
import org.example.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImp implements ValidateCodeService{

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1.通过hutool生成验证码图片
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        // 获取生成的value值和验证码图片值
        String codeValue = circleCaptcha.getCode();
        String codeImg = circleCaptcha.getImageBase64();

        // 2.生成UUID作为验证码的key值
        String key = UUID.randomUUID().toString().replace("-", "");
        // 3.将生成的验证码存放入Redis中
        redisTemplate.opsForValue().set("user:login:validatecode:"+key, codeValue,5,TimeUnit.MINUTES);
        // 4.返回响应结果
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + codeImg);

        return validateCodeVo;
    }
}
