package com.hspedu.furns.filter;

import com.google.gson.Gson;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于权限验证的过滤器，对指定url进行验证
 * 有登录 放行
 */
public class AuthFilter implements Filter {
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // tomcat会初始化过滤器实例
        String strExcludedUrls = filterConfig.getInitParameter("excludedUrls");
        String[] splitUrl = strExcludedUrls.split(",");
        // splitUrl转成List
        excludedUrls = Arrays.asList(splitUrl);
//        System.out.println("excludedUrls= " + excludedUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 得到请求的url
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getServletPath();
        System.out.println("url=" + url);
        if (!excludedUrls.contains(url)) {
            // 得到session中的member对象
            Member member = (Member) request.getSession().getAttribute("member");
            if (member == null) {
                if (!WebUtils.isAjaxRequest(request)) {
                    request.getRequestDispatcher("/views/member/login.jsp").
                            forward(servletRequest, servletResponse);
                } else {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("url", "views/member/login.jsp");
                    String resultJson = new Gson().toJson(resultMap);
                    servletResponse.getWriter().write(resultJson);
                }
                // 转发到登录页面 转发不走过滤器
                // 请求转发： 浏览器->server->找到资源->返回给浏览器
                // 重定向： 浏览器 -> server -> 打给浏览器 -> server
//            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            response.sendRedirect("/views/member/login.jsp");
                // 返回
                return;
            } else {
                // 不是admin 不允许访问后台
                if (url.contains("/manage") && !"admin".equals(member.getUsername())) {
                    request.getRequestDispatcher("/views/manage/manage_login.jsp").
                            forward(servletRequest, servletResponse);
                    return;
                }
            }
        }
        // 继续访问目标资源
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
