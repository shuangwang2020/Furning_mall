package com.hspedu.furns.test;

import com.hspedu.furns.entity.Admin;
import com.hspedu.furns.service.AdminService;
import com.hspedu.furns.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;

public class AdminServiceTest {
    private AdminService adminService = new AdminServiceImpl();

    @Test
    public void loginTest() {
        Admin admin = new Admin(null, "admin", "admin", null);
        System.out.println(adminService.login(admin));
    }

}
