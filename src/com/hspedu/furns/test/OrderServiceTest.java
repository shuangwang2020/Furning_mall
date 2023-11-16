package com.hspedu.furns.test;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void saveOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "北欧风格小桌子",
                new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        cart.addItem(new CartItem(2, "简约风格小椅子",
                new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        System.out.println(orderService.saveOrder(cart, 2));
    }

    @Test
    public void listOrder() {
        List<Order> orders = orderService.listOrder(15);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
