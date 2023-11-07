package com.hspedu.furns.test;

import com.hspedu.furns.utils.JDBCUtilsByDruid;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtilsByDruidTest {
    @Test
    public void getConnection() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println("connection= " + connection);
        JDBCUtilsByDruid.close(null, null, connection);
    }
}
