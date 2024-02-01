package org.example.manager.service.imp;

import org.example.manager.mapper.AsyncOperLogMapper;
import org.example.log.service.AsyncOperLogService;
import org.example.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImp implements AsyncOperLogService {

    @Autowired
    private AsyncOperLogMapper asyncOperLogMapper;

    @Override
    @Async
    public void saveSysOperLog(SysOperLog sysOperLog) {
        asyncOperLogMapper.save(sysOperLog);
    }
}
