package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public class FurnServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        Page<Furn> page = furnService.page(pageNo, pageSize);
        System.out.println("page=" + page);

        // 将page放入request域
        req.setAttribute("page", page);

        // 请求转发到furn_manage页面
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 将提交修改的家居信息封装成Furn
        Furn furn =
                DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
//        System.out.println("bean= " + furn);
        furnService.updateFurn(furn);

        // 重定向到家居列表页，看到最新数据
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 处理回显家居信息的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);

        // 放入request域中
        req.setAttribute("furn", furn);
//        System.out.println("furn= " + furn);

        // 将pageNo保存到request域
//        req.setAttribute("pageNo", req.getParameter("pageNo"));
        // 请求带来的参数 而且是请求转发到下一个页面 下一个页面可以通过param.pageNo取出来
        req.getRequestDispatcher("/views/manage/furn_update.jsp").forward(req, resp);
    }

    protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使用一个工具方法，可以转成数字的字符串就转 否则返回一个默认值
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        furnService.deleteFurn(id);

        // 重定向到家居列表页，看到最新数据
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取到添加家居信息
//        String name = req.getParameter("name");
//        String maker = req.getParameter("maker");
//        String price = req.getParameter("price");
//        String sales = req.getParameter("sales");
//        String stock = req.getParameter("stock");
//        System.out.println("stock= " + stock);
//        String imgPath = req.getParameter("imgPath");
//        System.out.println("imgPath= " + imgPath);
//        try {
//            int i = Integer.parseInt(sales);
//        } catch (NumberFormatException e) {
//            System.out.println("转换失败...");
//            req.setAttribute("msg", "销量数据格式不对...");
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
//            return;
//        }
//        Furn furn = null;
//        try {
//             furn = new Furn(null, name, maker, new BigDecimal(price), new Integer(sales), new Integer(stock),
//                    "/assets/images/product-image/default.jpg");
//        } catch (NumberFormatException e) {
////            System.out.println("转换失败...");
//            req.setAttribute("msg", "添加数据格式不对...");
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
//            return;
//        }

//        Furn furn = new Furn(null, name, maker, new BigDecimal(price), new Integer(sales), new Integer(stock),
//                    "/assets/images/product-image/default.jpg");

        // 使用BeanUtils完成javabean对象的自动封装
//        Furn furn = new Furn();
//        System.out.println("111= " + furn);
//        try {
//            // 将req.getParameterMap()数据封装到furn对象
//            // 使用反射将数据封装 前提：表单提交的数据 字段名要和要封装的javabean属性名一致
//            BeanUtils.populate(furn, req.getParameterMap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(furn);
        // 自动封装
        Furn furn =
                DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
        furnService.addFurn(furn);

        // 请求转发到家居显示页面，即需要重新走一下FurnServlet-list方法
//        req.getRequestDispatcher("/manage/furnServlet?action=list").forward(req, resp);
        // 使用请求转发 当用户刷新页面时，会重新发出一次请求 会造成数据重复提交
        // 因为重定向实际是让浏览器重新发请求，所以我们回送的url最好是个完整的url
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));

    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Furn> furns = furnService.queryFurns();
        // 把furns集合放入到request域
        req.setAttribute("furns", furns);

        // 请求转发效率快一点
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }
}
