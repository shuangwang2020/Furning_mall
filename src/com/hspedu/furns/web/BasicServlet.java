package com.hspedu.furns.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BasicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决接收到的数据中文乱码问题 一定要在最开始的地方设置接收编码
        req.setCharacterEncoding("utf-8");
        //        System.out.println("BasicServlet doPost()...");
        // 要满足action的value和方法名一致!!!
        String action = req.getParameter("action");
        System.out.println("action= " + action);
        // 使用反射，获取当前对象方法
        // 1.this就是请求的servlet
        System.out.println("this= " + this);
        // 2.declaredMethod 方法对象就是当前请求的servlet对应的action名字的方法
        try {
            Method declaredMethod =
                    this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println("declaredMethod " + declaredMethod);
            declaredMethod.invoke(this, req, resp);
        } catch (Exception e) {
            // 心得体会：异常可以参与业务逻辑  默认的异常处理机制
            throw new RuntimeException(e);
        }
    }

    // 增加处理GET请求

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
