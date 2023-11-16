package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.entity.Order;

import java.util.List;

public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO {

    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO `order`(`id`,`create_time`,`price`,`status`,`member_id`) " +
                "VALUES(?,?,?,?,?)";
        return update(sql, order.getId(), order.getCreateTime(),
                order.getPrice(), order.getStatus(), order.getMemberId());
    }

    @Override
    public List<Order> listOrder(int memberId) {
        String sql = "SELECT `id`, `create_time` createTime, `price`, `status`, `member_id` memberId" +
                " FROM `order` WHERE `member_id` = ?";
        return queryMulti(sql, Order.class, memberId);
    }


}
