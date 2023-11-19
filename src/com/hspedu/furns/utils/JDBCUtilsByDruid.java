package com.hspedu.furns.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 韩顺平
 * @version 1.0
 * 基于druid数据库连接池的工具类
 */
public class JDBCUtilsByDruid {
    private static DataSource ds;

    // 定义属性 ThreadLocal 存放connection
    private static ThreadLocal<Connection> threadLocalConn =
            new ThreadLocal<>();

    //在静态代码块完成 ds初始化
    static {
        Properties properties = new Properties();
        try {
            // web项目，工作目录在out下，文件的加载，需要使用类加载器
            // 找到我们的工作目录
            properties.load(JDBCUtilsByDruid.class.getClassLoader().
                    getResourceAsStream("druid.properties"));
           // properties.load(new FileInputStream("src\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //编写getConnection方法
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }

    /**
     * 从ThreadLocal获取connection 保证同一个线程中获取的是同一个connection
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        Connection connection = threadLocalConn.get();
        if (connection == null) { // 说明当前threadLocal中没有连接
            try {
                connection = ds.getConnection();
                connection.setAutoCommit(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    public static void commit() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close(); // 释放
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // 1.提交后，需要把connection从ThreadLocal清除掉
        // 2, 不然，会造成threadLocalConn长时间持有该连接，会影响效率
        // 3. 同时因为tomcat底层使用的是线程池技术 造成2
        threadLocalConn.remove();
    }

    /**
     * 回滚/撤销跟连接相关的操作 删除，修改，添加
     */
    public static void rollBack() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        threadLocalConn.remove();
    }

    //关闭连接, 老师再次强调： 在数据库连接池技术中，close 不是真的断掉连接
    //而是把使用的Connection对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
