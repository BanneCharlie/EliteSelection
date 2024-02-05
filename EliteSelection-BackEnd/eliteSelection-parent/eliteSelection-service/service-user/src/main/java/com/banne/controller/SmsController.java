package com.banne.controller;

import com.banne.service.SmsService;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ClientSmsController")
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone) {
        smsService.sendValidateCode(phone);
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }


}
