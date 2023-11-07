package com.hspedu.furns.test;

import com.hspedu.furns.dao.AdminDAO;
import com.hspedu.furns.dao.impl.AdminDAOImpl;
import com.hspedu.furns.entity.Admin;
import org.junit.jupiter.api.Test;

public class AdminDAOTest {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Test
    public void queryAdminByUsernameAndPasswordTest() {
        Admin admin = adminDAO.queryAdminByUsernameAndPassword("admin", "admin");
        System.out.println("admin= " + admin);
    }
}
