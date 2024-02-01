package org.example.log.service;

import org.example.model.entity.system.SysOperLog;
import org.springframework.stereotype.Service;

@Service
public interface AsyncOperLogService {
    void saveSysOperLog(SysOperLog sysOperLog);
}
