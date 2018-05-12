package top.kwseeker.JdbcConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库设置存放在XML文件
 * 实现一个建立连接的方法（从XML中获取配置）
 */
public class Jdbc4Connection {

    private Connection conn = null;
    private Properties connectionProps = null;

    /*=通过DataSource连接（池化连接）==================================*/
    // DataSource又有3种连接方式： 直连数据库（内部实现仍为DriverManager） + 池化连接 + 分布式服务连接


    /*=通过DriverManager连接==========================================*/
    /**
     * 从properties xml文件中加载配置进行连接
     * @param dbUrl 数据库URL key
     * @param user  用户 key
     * @param password 密码 key
     * @param xmlPath xml文件路径
     */
    public Jdbc4Connection(String dbUrl, String user, String password, String xmlPath) {
        try {
            FileInputStream fis = new FileInputStream(xmlPath);
            connectionProps = getPropertiesFromXML(fis);
            conn = DriverManager.getConnection(connectionProps.getProperty(dbUrl),
                    connectionProps.getProperty(user),
                    connectionProps.getProperty(password));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从inputStream 加载配置进行连接
     * @param dbUrl 数据库URL key
     * @param user  用户 key
     * @param password 密码 key
     * @param inputStream 输入流
     */
    public Jdbc4Connection(String dbUrl, String user, String password, InputStream inputStream) {
        try {
                connectionProps = getPropertiesFromXML(inputStream);
                conn = DriverManager.getConnection(connectionProps.getProperty(dbUrl),
                        connectionProps.getProperty(user),
                        connectionProps.getProperty(password));
                // conn.setCatalog();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * 直接传参连接
     * @param dbUrl
     * @param user
     * @param password
     */
    public Jdbc4Connection(String dbUrl, String user, String password) {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private Properties getPropertiesFromXML(InputStream inputStream) {
        Properties prop = new Properties();
        try {
            prop.loadFromXML(inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public Connection getConn() {
        if(conn != null) {
            return conn;
        } else {
            // TODO ; throw exception
            System.out.println("Error getConn(): null pointer!");
            return null;
        }
    }

    public void closeConn() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    /*
    <entry key="db_url">jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8&useSSL=true&serverTimezone=UTC</entry>
    <entry key="user_name">root</entry>
    <entry key="password">root</entry>

    <entry key="dbms">mysql</entry>
    <entry key="driver">com.mysql.cj.jdbc.Driver</entry>
    <entry key="database_name">shopping</entry>
    <entry key="server_name">localhost</entry>
    <entry key="port_number">3306</entry>
    */
}
