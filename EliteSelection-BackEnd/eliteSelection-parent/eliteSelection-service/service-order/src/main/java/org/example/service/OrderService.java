package org.example.service;

import org.example.model.vo.h5.TradeVo;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    TradeVo getTrade();
}
