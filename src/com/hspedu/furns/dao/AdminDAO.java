package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Admin;

public interface AdminDAO {
    Admin queryAdminByUsernameAndPassword(String username, String password);
}
