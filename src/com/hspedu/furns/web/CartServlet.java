package com.hspedu.furns.web;


import com.google.gson.Gson;
import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.clear();
        }
        // 返回清空购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    protected void delItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);

        // 获取到cart购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.delItem(id);
        }
        // 返回到删除购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 更新某个CartItem的数量
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        int count = DataUtils.parseInt(req.getParameter("count"), 1);

        // 获取session中的购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.updateCount(id, count);
        }

        // 回到请求更新购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    // 添加家居购物信息到购物车
    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);

        Furn furn = furnService.queryFurnById(id);

        if (furn == null) {
            return;
        }

        if (furn.getStock() <= 0) {
            System.out.println("库存不足");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        // 根据furn构建CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        // 从session中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        // 将cartItem加入到cart对象
        cart.addItem(item);

        // 添加完毕后返回json数据给前端 进行局部刷新 {"cartTotalCount": 3}
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cartTotalCount", cart.getTotalCount());
        String resultJson = new Gson().toJson(resultMap);
        resp.getWriter().write(resultJson);
    }

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);

        Furn furn = furnService.queryFurnById(id);

        if (furn == null) {
            return;
        }

        if (furn.getStock() <= 0) {
            System.out.println("库存不足");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        // 根据furn构建CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        // 从session中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        // 将cartItem加入到cart对象
        cart.addItem(item);
        System.out.println("cart= " + cart);

        // 添加完毕后返回添加家居页面

        resp.sendRedirect(req.getHeader("Referer"));
    }
}
