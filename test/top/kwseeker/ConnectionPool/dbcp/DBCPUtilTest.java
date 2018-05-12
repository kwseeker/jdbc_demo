package top.kwseeker.ConnectionPool.dbcp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DBCPUtilTest {

    private DBCPUtil dbcpUtil = new DBCPUtil();
    private long timeBefore;

    @Before
    public void countTimeBefore() {
        timeBefore = new Date().getTime();
    }

    @After
    public void countTimeAfter() {
        long timeAfter = new Date().getTime();
        System.out.println("测试方法用时： " + (timeAfter-timeBefore) + "ms");
    }

    @Test
    public void testReadDBQuery() {
        String sqlExpression = "select id, name, city, price from items";
        ResultSet resultSet = dbcpUtil.readDBQuery(sqlExpression);
        try {
            resultSet.next();
            assertEquals("沃特篮球鞋", resultSet.getString(2));  //索引号从1开始
            while (resultSet.next()) {
                if(resultSet.isLast()){
                    assertEquals(5999, resultSet.getInt(4));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // TODO: 添加测试完事务回滚
    @Test
    public void testWriteDBUpdate() {
        Connection conn = DBCPUtil.getConnection();
        try {
            conn.setAutoCommit(false);  //关闭自动提交事务
            for (int i = 0; i < 10; i++) {
                String sqlExpression = "INSERT INTO `items` VALUES ('" + (1031+i)
                        + "', 'ipad5', 'shang', '5999', '500', '010.jpg');";
                conn.createStatement().executeUpdate(sqlExpression);
                if(i==5){
                    conn.rollback();
                }
            }
//            throw new SQLException("模拟SQL异常...");
            conn.commit();  //提交事务
        } catch (Exception e) {
            try {
                conn.rollback();    //事务回滚
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

}
