package org.example.controller;

import org.example.model.vo.common.*;
import org.example.model.vo.h5.TradeVo;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ClientOrderController")
@RequestMapping(value="/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }
}
