package org.example.manager.controller;

import org.example.manager.service.OrderService;
import org.example.model.dto.order.OrderStatisticsDto;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.common.Result;
import org.example.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderService.getOrderStatisticsData(orderStatisticsDto) ;
        return Result.build(orderStatisticsVo , ResultCodeEnum.SUCCESS) ;
    }
}
