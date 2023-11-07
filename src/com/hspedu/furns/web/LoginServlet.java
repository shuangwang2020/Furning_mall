package com.hspedu.furns.web;

import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;
import com.hspedu.furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private MemberService memberService = new MemberServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("Login Servlet被调用...");
        // 如果再登录界面，用户没有提交内容，就直接提交，后台接收到的是""
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Member member = memberService.login(new Member(null, username, password, null));
        if (member == null) {
//            System.out.println(member + " 登录失败...");
            // 把登录错误信息放入到request域
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("username", username);

            // 页面转发
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        } else {
//            System.out.println(member + " 登录成功...");
            request.getRequestDispatcher("/views/member/login_ok.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
