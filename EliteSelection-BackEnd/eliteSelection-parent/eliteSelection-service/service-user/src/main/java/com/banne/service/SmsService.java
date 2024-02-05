package com.banne.service;

import org.springframework.stereotype.Service;

@Service
public interface SmsService {

    void sendValidateCode(String phone);
}
