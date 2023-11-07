package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.AdminDAO;
import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.entity.Admin;

public class AdminDAOImpl extends BasicDAO<Admin> implements AdminDAO {
    @Override
    public Admin queryAdminByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `admin`" +
                " WHERE `username`=? AND `password`=md5(?)";
        return querySingle(sql, Admin.class, username, password);
    }
}
