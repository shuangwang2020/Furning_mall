package com.hspedu.furns.service;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.entity.OrderItem;

import java.util.List;

public interface OrderService {
    // 生成订单 cart在session中 通过web层,传入saveOrder
    // 订单是和一个会员关联的
    String saveOrder(Cart cart, int memberId);

    List<Order> listOrder(int memberId);

    List<OrderItem> listOrderDetails(String orderId);
}
