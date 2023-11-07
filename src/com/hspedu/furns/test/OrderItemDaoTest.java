package com.hspedu.furns.test;

import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.OrderItemDaoImpl;
import com.hspedu.furns.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderItemDaoTest {
    private OrderItemDAO orderItemDAO = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {
        OrderItem orderItem = new OrderItem(null, "北欧小沙发~",
                new BigDecimal(200), 2, new BigDecimal(400), "sn00002");
        System.out.println(orderItemDAO.saveOrderItem(orderItem));
    }
}
