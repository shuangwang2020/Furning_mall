package com.hspedu.furns.web;

import com.hspedu.furns.entity.Admin;
import com.hspedu.furns.service.AdminService;
import com.hspedu.furns.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends BasicServlet {
    // test
    private AdminService adminService = new AdminServiceImpl();

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = adminService.login(new Admin(null, username, password, null));
        if (admin == null) {
//            System.out.println(member + " 登录失败...");
            // 把登录错误信息放入到request域
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("username", username);

            // 页面转发
            request.getRequestDispatcher("/views/manage/manage_login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/views/manage/manage_menu.jsp")
                    .forward(request, response);
        }
    }
}
