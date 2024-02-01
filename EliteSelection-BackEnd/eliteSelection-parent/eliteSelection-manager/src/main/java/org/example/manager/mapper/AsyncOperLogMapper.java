package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.system.SysOperLog;

@Mapper
public interface AsyncOperLogMapper {
    void save(SysOperLog sysOperLog);
}
