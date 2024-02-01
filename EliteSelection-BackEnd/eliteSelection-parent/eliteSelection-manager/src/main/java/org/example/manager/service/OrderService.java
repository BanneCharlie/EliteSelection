package org.example.manager.service;

import org.example.model.dto.order.OrderStatisticsDto;
import org.example.model.vo.order.OrderStatisticsVo;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
