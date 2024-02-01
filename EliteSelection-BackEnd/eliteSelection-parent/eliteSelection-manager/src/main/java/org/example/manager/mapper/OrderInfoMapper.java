package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.order.OrderStatistics;

@Mapper
public interface OrderInfoMapper {
    OrderStatistics selectOrderStatistics(String createTime);
}
