package top.kwseeker.ConnectionPool.dbcp;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPUtil {

    private static final String configFile = "resources/dbcp.properties";
    private static DataSource dataSource;

    /**
     * 静态代码块属于类，在类加载时调用
     */
    static {
//        System.out.println(new File(configFile).getAbsolutePath());
        Properties properties = new Properties();
        try{
            FileInputStream inputStream = new FileInputStream(configFile);
//            properties.load(Object.class.getResourceAsStream(configFile));
            properties.load(inputStream);
        }  catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public DBCPUtil() {
        initDbcp();
    }

    private void initDbcp() {
        Properties properties = new Properties();
        try{
            FileInputStream inputStream = new FileInputStream(configFile);
//            properties.load(Object.class.getResourceAsStream(configFile));
            properties.load(inputStream);
        }  catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 从连接池获取一个连接
      * @return 连接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 没有意义的方法只是测试用
     * @param sqlExpr
     * @return
     */
    public ResultSet readDBQuery(String sqlExpr) {
        try {
            Connection conn = DBCPUtil.getConnection();
            System.out.println("getConnection done ...");
            return conn.createStatement().executeQuery(sqlExpr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("读取数据库失败");
    }

}
