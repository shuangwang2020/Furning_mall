package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.dao.impl.OrderDAOImpl;
import com.hspedu.furns.dao.impl.OrderItemDaoImpl;
import com.hspedu.furns.entity.*;
import com.hspedu.furns.service.OrderService;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();

    private OrderItemDAO  orderItemDAO = new OrderItemDaoImpl();

    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public String saveOrder(Cart cart, int memberId) {
        // 购物车数据 -> order和orderItem形式保存到db
        // uuid表示当前订单号 要保证唯一的
        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order = new Order(orderId, new Date(), cart.getCartTotalPrice(), 0, memberId);
        orderDAO.saveOrder(order);

        // 通过cart对象 遍历 cartItem 构建OrderItem
//        HashMap<Integer, CartItem> items = cart.getItems();
//        Set<Integer> keys = items.keySet();
//        for (Integer id : keys) {
//            CartItem cartItem = items.get(id);
//            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getPrice(),
//                    cartItem.getCount(), cartItem.getTotalPrice(), orderId);
//
//            // 保存
//            orderItemDAO.saveOrderItem(orderItem);
//
//            // 更新furn表
//            Furn furn = furnDAO.queryFurnById(id);
//            furn.setSales(furn.getSales() + cartItem.getCount());
//            furn.setStock(furn.getStock() - cartItem.getCount());
//            furnDAO.updateFurn(furn);
//        }

        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getPrice(),
                    cartItem.getCount(), cartItem.getTotalPrice(), orderId);

            // 保存
            orderItemDAO.saveOrderItem(orderItem);

            // 更新furn表
            Furn furn = furnDAO.queryFurnById(cartItem.getId());
            furn.setSales(furn.getSales() + cartItem.getCount());
            furn.setStock(furn.getStock() - cartItem.getCount());
            furnDAO.updateFurn(furn);
        }
        // 清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> listOrder(int memberId) {
        return orderDAO.listOrder(memberId);
    }

    @Override
    public List<OrderItem> listOrderDetails(String orderId) {
        return orderItemDAO.listOrderDetails(orderId);
    }
}
