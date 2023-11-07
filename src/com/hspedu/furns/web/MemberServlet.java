package com.hspedu.furns.web;

import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;
import com.hspedu.furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class MemberServlet extends BasicServlet {
    private MemberService memberService = new MemberServiceImpl();

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
////        System.out.println("action= " + action);
//        response.setContentType("text/html;charset=utf-8");
//        if ("login".equals(action)) {
//            login(request, response);
//        } else if ("register".equals(action)){
//            register(request, response);
//        } else {
//            response.getWriter().write("请求参数action有误");
//        }
//    }


    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 销毁当前用户的session
        req.getSession().invalidate();

        // 重定向到网站首页 -> 刷新首页
        resp.sendRedirect(req.getContextPath());

    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=====memberServlet login()=====");
        //        System.out.println("Login Servlet被调用...");
        // 如果再登录界面，用户没有提交内容，就直接提交，后台接收到的是""
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Member member = memberService.login(new Member(null, username, password, null));
        if (member == null) { // 用户没有在DB
//            System.out.println(member + " 登录失败...");
            // 把登录错误信息放入到request域
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("username", username);

            // 页面转发
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        } else {
//            System.out.println(member + " 登录成功...");
            request.getSession().setAttribute("member", member);
            request.getRequestDispatcher("/views/member/login_ok.jsp").forward(request, response);
        }
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=====memberServlet register()=====");
//        System.out.println("RegisterServlet被调用...");
        // 接收用户注册信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // 获取用户提交的验证码
        String code = request.getParameter("code");

        // 从session中获取到验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);

        // 立即删除session中的验证码，防止该验证码被重复使用
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        // 防止大并发下验证码已经失效
        if (token != null && token.equalsIgnoreCase(code)) {
            // 判断用户名是否可用
            if (!memberService.isExistsUsername(username)) {
                // 注册
//            System.out.println("用户名= " + username + " 不存在，可以注册");
                Member member = new Member(null, username, password, email);
                if (memberService.registerMember(member)) {
//                System.out.println("注册成功~");
                    // 请求转发
                    request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request, response);
                } else {
                    System.out.println("注册error~~~");
                    request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request, response);
                }
            } else {
                // 返回注册页面
                // 后面可以加入提示信息
                request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("register_msg", "验证码不正确~");

            // 前端需要回显某些数据
            request.setAttribute("username", username);
            request.setAttribute("email", email);

            // 带回一个信息 要显示到注册选项页
            request.setAttribute("active", "register");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        }
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
}
