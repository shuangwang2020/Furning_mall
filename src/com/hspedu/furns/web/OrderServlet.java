package com.hspedu.furns.web;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.entity.OrderItem;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zpstart
 * @create 2023-11-13 22:27
 */

public class OrderServlet extends BasicServlet {
    private OrderService orderService = new OrderServiceImpl();

    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        // 没有购物车或者购物车是空的，但是没有家居数据
        if (null == cart || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // 获取当前登录的memberId
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {
            // 说明用户没有登录
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
            return;
        }
        // 就可以生成订单
        String orderId = orderService.saveOrder(cart, member.getId());
        req.getSession().setAttribute("orderId", orderId);
        // 重定向到checkout.jsp
        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");
    }

    protected void listOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取当前登录的memberId
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {
            // 说明用户没有登录
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
            return;
        }
        // 根据用户id去order表查询订单
        System.out.println(member.getId());
        List<Order> orders = orderService.listOrder(member.getId());
        System.out.println("orders=" + orders);
        req.getSession().setAttribute("orders", orders);
        req.getRequestDispatcher("/views/order/order.jsp").forward(req, resp);
    }

    protected void listOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        System.out.println("orderId" + orderId);
        List<OrderItem> orderItems = orderService.listOrderDetails(orderId);
        int totalCount = orderItems.stream().mapToInt(OrderItem::getCount).sum();

        BigDecimal sum = new BigDecimal("0");
        for (OrderItem orderItem : orderItems) {
            sum = sum.add(orderItem.getTotalPrice());
        }
        req.getSession().setAttribute("orderItems", orderItems);
        req.getSession().setAttribute("totalCount", totalCount);
        req.getSession().setAttribute("sum", sum);
        req.getRequestDispatcher("/views/order/order_detail.jsp").forward(req, resp);
    }
}