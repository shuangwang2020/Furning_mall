package com.hspedu.furns.filter;

import com.hspedu.furns.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

/**
 * 管理事务的过滤器
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 放行
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtilsByDruid.commit(); // 统一提交
        } catch (Exception e) {
            JDBCUtilsByDruid.rollBack();
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
