package com.hspedu.furns.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    // 文件上传路径
    public static final String FURN_IMG_DIRECTORY = "assets/images/product-image";
    /**
     * 判断一个请求是不是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
