package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;
import com.hspedu.furns.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        System.out.println("id=" + id);
        // 获取到furn对象
        Furn furn = furnService.queryFurnById(id);
        if (furn == null) {
            return;
        }

        if (ServletFileUpload.isMultipartContent(req)) {
            //System.out.println("OK");
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象
            /**
             *     表单提交的数据就是 input 元素
             *     <input type="file" name="pic" id="" value="2xxx.jpg" onchange="prev(this)"/>
             *     家居名: <input type="text" name="name"><br/>
             *     <input type="submit" value="上传"/>
             */
            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            //   老师的编程心得体会: 如果我们不知道一个对象是什么结构[1.输出 2.debug 3. 底层自动看到]
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                /*
                list==>

                [name=3.jpg, StoreLocation=D:\hspedu_javaweb\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\temp\xupload__7e34374f_17fce4168b1__7f4b_00000000.tmp, size=106398bytes, isFormField=false, FieldName=pic,
                name=null, StoreLocation=D:\hspedu_javaweb\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\temp\xupload__7e34374f_17fce4168b1__7f4b_00000001.tmp, size=6bytes, isFormField=true, FieldName=name]

                 */
                //System.out.println("list==>" + list);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //System.out.println("fileItem=" + fileItem);
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text
//                        String name = fileItem.getString("utf-8");
//                        System.out.println("家具名=" + name);
                        if ("name".equals(fileItem.getFieldName())) {
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())){
                             furn.setStock(new Integer(fileItem.getString()));
                        }
                    } else {//是一个文件
                        //用一个方法
                        //获取上传的文件的名字
                        String name = fileItem.getName();
//                        System.out.println("上传的文件名=" + name);
                        // 如果用户没有选择图片，name="" 如果等于""，不做处理
                        if (!"".equals(name)) {
                            //把这个上传到 服务器的 temp下的文件保存到你指定的目录
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + WebUtils.FURN_IMG_DIRECTORY; // 从网站根目录开始算
                            //2. 获取到完整目录 [io/servlet基础]
                            //  这个目录是和你的web项目运行环境绑定的. 是动态.
                            //fileRealPath=D:\hspedu_javaweb\fileupdown\out\artifacts\fileupdown_war_exploded\
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);
                            System.out.println("fileRealPath=" + fileRealPath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath + "/" + WebUtils.getYearMonthDay());
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" +System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" +name;
                            fileItem.write(new File(fileFullPath));
                            fileItem.getOutputStream().close();
//                        //5. 提示信息
//                        resp.setContentType("text/html;ch-arset=utf-8");
//                        resp.getWriter().write("上传成功~");

                            // 更新图片路径
                            String originalImgPath = furn.getImgPath();
                            furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + WebUtils.getYearMonthDay() + name);

                            // 删除原来的图片
                            if (!"".equals(originalImgPath) || originalImgPath != null) {
                                String oriRealPath = req.getServletContext().getRealPath("/" + originalImgPath);
                                File oriFileFullPath = new File(oriRealPath);
                                if (oriFileFullPath.exists()) {
                                    oriFileFullPath.delete();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        furnService.updateFurn(furn);
        // 请求转发到更新成功的页面
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);
    }

    //    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 将提交修改的家居信息封装成Furn
//        Furn furn =
//                DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
////        System.out.println("bean= " + furn);
//        furnService.updateFurn(furn);
//
//        // 重定向到家居列表页，看到最新数据
//        resp.sendRedirect(req.getContextPath() +
//                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
//    }

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
