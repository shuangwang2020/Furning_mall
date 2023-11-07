package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.entity.OrderItem;

public class OrderItemDaoImpl extends BasicDAO<OrderItem> implements OrderItemDAO {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO `order_item`(`id`,`name`,`price`,`count`,`total_price`,`order_id`) " +
                "VALUES(?,?,?,?,?,?)";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getPrice(),
                orderItem.getCount(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
