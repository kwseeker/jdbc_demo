package top.kwseeker.JdbcExecuteSQL;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRowSets {

    private Connection conn = null;

    JdbcRowSets(Connection conn) {
        this.conn = conn;
    }

    public void testJdbcRowSet() throws SQLException{
        // 1 passing ResultSet Object 创建 JdbcRowSet对象
//        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//                ResultSet.CONCUR_UPDATABLE);
//        ResultSet resultSet = statement.executeQuery("select * from items");
//        JdbcRowSet jdbcRowSet = new JdbcRowSetImpl(resultSet);

        // 2 passing Connection Object 创建 JdbcRowSet对象
        JdbcRowSet jdbcRowSet1 = new JdbcRowSetImpl(conn);
        jdbcRowSet1.setCommand("select * from items");
        jdbcRowSet1.execute();
//        System.out.println("Connection Url:" + jdbcRowSet1.getUrl());   // 为什么得到的是空的？

        // 3 using the default constructor
//        JdbcRowSet jdbcRowSet2 = new JdbcRowSetImpl();
//        jdbcRowSet2.setCommand("select * from items");
//        jdbcRowSet2.setUrl("jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8&amp;useSSL=true&amp;serverTimezone=UTC"); // 显示格式错误？
//        jdbcRowSet2.setUsername("root");
//        jdbcRowSet2.setPassword("112358");
//        jdbcRowSet2.execute();

        // 4 using the RowSetFactory interface
//        RowSetFactory rowSetFactory = null;
//        JdbcRowSet jdbcRowSet3 = null;
//        ResultSet rs =null;
//        Statement statement3 =null;
//        rowSetFactory = RowSetProvider.newFactory();
//        jdbcRowSet3 = rowSetFactory.createJdbcRowSet();
//        jdbcRowSet3.setUrl("jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8&amp;useSSL=true&amp;serverTimezone=UTC");
//        jdbcRowSet3.setUsername("root");
//        jdbcRowSet3.setPassword("112358");
//        jdbcRowSet3.setCommand("select * from items");
//        jdbcRowSet3.execute();

        // JdbcRowSet的使用
        // 改
//        jdbcRowSet.absolute(4);
//        jdbcRowSet.updateInt("price", 88);  //updateRow() 或 insertRow()之后才会生效
//        jdbcRowSet.updateRow();
//        jdbcRowSet.previous();
//        jdbcRowSet.updateInt("price", 350);
//        jdbcRowSet.updateRow();
        // 增
//        jdbcRowSet1.moveToInsertRow();
//        jdbcRowSet1.updateInt("id", 8);
//        jdbcRowSet1.updateString("name", "Huawei Mate10");
//        jdbcRowSet1.updateString("supplierid", "00000008");
//        jdbcRowSet1.updateString("city", "东莞");
//        jdbcRowSet1.updateInt("price", 3499);
//        jdbcRowSet1.updateInt("number", 500);
//        jdbcRowSet1.updateString("picture", "008.jpg");
//        jdbcRowSet1.insertRow();
        // 查
        jdbcRowSet1.last();
//        jdbcRowSet2.getInt("price");
        System.out.println("Price: " + jdbcRowSet1.getInt("price"));
        // 删
        System.out.println("Row index: " + jdbcRowSet1.getRow());
        jdbcRowSet1.last();
        jdbcRowSet1.previous();
        jdbcRowSet1.deleteRow();
    }

    public void testCachedRowSet() throws SQLException{

        CachedRowSet cachedRowSet = new CachedRowSetImpl();
        cachedRowSet.setUsername("root");
        cachedRowSet.setPassword("112358");
        cachedRowSet.setUrl("jdbc:mysql://localhost:3306/shopping?serverTimezone=UTC&useSSL=false");    // 分隔符用&
//        cachedRowSet.setUrl("jdbc:mysql://localhost:3306/shopping?serverTimezone=UTC&useSSL=false&elideSetAutoCommits=true");    // 分隔符用&
//        cachedRowSet.setUrl("jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8&amp;useSSL=true&amp;serverTimezone=UTC");    // XML中用 &amp;
        cachedRowSet.setCommand("select * from items");
        cachedRowSet.setPageSize(5);
        cachedRowSet.execute();
        cachedRowSet.addRowSetListener(new ExampleRowSetListener());

        int i = 1;
        do {
            System.out.println("Page number: " + i);
            while (cachedRowSet.next()) {
                System.out.println("Found item: " + cachedRowSet.getString("name"));
            }
            i++;
        } while (cachedRowSet.nextPage());

//        cachedRowSet.previousPage();
//        cachedRowSet.previousPage();
//        System.out.println("CurrentRow: " + cachedRowSet.getRow());
//        cachedRowSet.absolute(2);
//        cachedRowSet.updateInt("price", 80);
//        cachedRowSet.updateRow();
//        cachedRowSet.acceptChanges();

    }

    public void testJoinRowSet() {

    }

    public void testFilteredRowSet() {

    }

    public void testWebRowSet() {

    }

}
