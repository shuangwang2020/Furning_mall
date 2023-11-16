package com.hspedu.furns.dao;

import com.hspedu.furns.entity.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    int saveOrderItem(OrderItem orderItem);

    List<OrderItem> listOrderDetails(String orderId);
}
