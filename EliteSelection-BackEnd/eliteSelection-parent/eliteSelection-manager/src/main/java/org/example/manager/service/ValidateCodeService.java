package org.example.manager.service;

import org.example.model.vo.system.ValidateCodeVo;
import org.springframework.stereotype.Service;

@Service
public interface ValidateCodeService {

    ValidateCodeVo generateValidateCode();
}
