package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.AdminDAO;
import com.hspedu.furns.dao.impl.AdminDAOImpl;
import com.hspedu.furns.entity.Admin;
import com.hspedu.furns.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public Admin login(Admin admin) {
        return adminDAO.
                queryAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
    }
}
