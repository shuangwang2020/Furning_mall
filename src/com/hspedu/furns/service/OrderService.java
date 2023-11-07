package com.hspedu.furns.service;

import com.hspedu.furns.entity.Cart;

public interface OrderService {
    // 生成订单 cart在session中 通过web层,传入saveOrder
    // 订单是和一个会员关联的
    String saveOrder(Cart cart, int memberId);
}
