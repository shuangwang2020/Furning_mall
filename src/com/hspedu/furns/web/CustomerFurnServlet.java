package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerFurnServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    /**
     * 这里仍然是一个分页请求家居信息的API/方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        Page<Furn> page = furnService.page(pageNo, pageSize);
//        StringBuilder url =
//                new StringBuilder("customerFurnServlet?action=page");
//        page.setUrl(url.toString());
        System.out.println("page=" + page);

        // 将page放入request域
        req.setAttribute("page", page);

        // 请求转发到furn_manage页面
        req.getRequestDispatcher("/views/customer/index.jsp").forward(req, resp);
    }

    protected void pageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        String name = req.getParameter("name");
        if (null == name) {
            name = "";
        }

        Page<Furn> page = furnService.pageByName(pageNo, pageSize, name);
//        System.out.println("page=" + page);

        StringBuilder url =
                new StringBuilder("customerFurnServlet?action=pageByName");
        if (!"".equals(name)) {
            url.append("&name=").append(name);
        }
        page.setUrl(url.toString());

        // 将page放入request域
        req.setAttribute("page", page);

        // 请求转发到furn_manage页面
        req.getRequestDispatcher("/views/customer/index.jsp").forward(req, resp);
    }
}
