package top.kwseeker.JdbcConnection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class Jdbc4ConnectionTest {

    private Jdbc4Connection jdbc4Connection;
    private Connection conn;

    @Test
    public void getConn() throws Exception {
        jdbc4Connection = new Jdbc4Connection("db_url", "user_name",
                "password", "resources/mysql-conn-properties.xml");
        conn = jdbc4Connection.getConn();
        String sqlExpression = "select id, name, city, price from items";
        ResultSet resultSet = conn.createStatement().executeQuery(sqlExpression);
        try {
            resultSet.next();
            assertEquals(1, resultSet.getString(1));  //索引号从1开始
            while (resultSet.next()) {
                if(resultSet.isLast()){
                    assertEquals(5999, resultSet.getInt(4));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}