package com.hspedu.furns.web;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        // 获取当前登录的memberId
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {
            // 说明用户没有登录
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            return;
        }
        // 就可以生成订单
        String orderId = orderService.saveOrder(cart, member.getId());
        req.getSession().setAttribute("orderId", orderId);
        // 重定向到checkout.jsp
        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");
    }
}