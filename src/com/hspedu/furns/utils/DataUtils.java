package com.hspedu.furns.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class DataUtils {
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            // 将req.getParameterMap()数据封装到furn对象
            // 使用反射将数据封装 前提：表单提交的数据 字段名要和要封装的javabean
            // 属性名一致
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    // 将字符串转成一个整数，否则返回默认值
    public static int parseInt(String val, int defaultVal) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            System.out.println(val + " 格式不正确");
        }
        return defaultVal;
    }
}
