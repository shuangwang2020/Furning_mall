package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Order;

import java.util.List;

public interface OrderDAO {
    int saveOrder(Order order);

    List<Order> listOrder(int memberId);
}
