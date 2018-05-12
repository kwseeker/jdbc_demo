package top.kwseeker;

import com.mysql.cj.x.protobuf.MysqlxSession;
import top.kwseeker.util.PrintExceptionMsg;

import java.sql.*;
import java.util.Formatter;
import java.util.Properties;

/**
 * JDBC 4.0 最简Demo
 */
public class DriverManagerMain {

    /**
     * 后面将这些参数放到xml文件中，通过 Property.loadFromXML传入到程序代码中
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "112358";
    private static final String DB_NAME = "shopping";

    private static Connection conn = null;

    public static void main(String[] args) {
        // 1. Establishing a connection.
        // 两种连接方式 DriverManager 和 DataSource
        Properties connectionProps = new Properties();
        connectionProps.put("user", USERNAME);
        connectionProps.put("password", PASSWORD);
        try {
            conn = DriverManager.getConnection(DB_URL, connectionProps);
            conn.setCatalog(DB_NAME);           //
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println("Connected to database ...");

        try {
            // 2. Create a statement.
            // 有三种语句 createStatement PreparedStatement CallableStatement
            // ResultSet属性参数 TYPE_SCROLL_SENSITIVE可以借助workbench和debug模式测试或者编写多线程测试
            Statement stmt = conn.createStatement();    // 默认类型为 TYPE_FORWARD_ONLY CONCUR_READ_ONLY (只能向前滚，以及并发读)
            Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); //改成CONCUR_READ_ONLY后再用此statement更新数据会报错
            Statement stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);    // 1
            System.out.println("ResultSetHoldability: " + stmt3.getResultSetHoldability());
            // TODO: PreparedStatement
            // Statement stmtPrepared = conn.prepareStatement();
            // TODO: CallableStatement
            // Statement stmtCallable = conn.prepareCall();

            // 3. Execute the query.
            String sqlQuery = "select id, name, city, price from items";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            int rowCount = stmt2.executeUpdate("DELETE FROM items WHERE id > 7");  //不区分大小写

            // 4. Process the ResultSet object.
            Formatter formatter = new Formatter(System.out);
            formatter.format("%-3s\t %-11s\t %-7s\t %-7s\t\n", "id", "name", "city", "price");
            while (resultSet.next()) {
                formatter.format("%-3d\t %-11s\t %-7s\t %-7d\t\n",
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("city"),
                        resultSet.getInt("price"));
            }

            System.out.println("isAfterLast: " + (resultSet.isAfterLast()?"true":"false"));

            ResultSet resultSet2 = stmt2.executeQuery(sqlQuery);
            while (resultSet2.next()) {
                float f = resultSet2.getInt("PRICE");   //不区分大小写
                resultSet2.updateInt( "PRICE", 99);
                resultSet2.updateString( "city", "深圳");
                resultSet2.updateRow(); // 执行这句才会真正更新数据库
            }

            stmt.close();
            stmt2.close();
        } catch (SQLException se) {
//            se.printStackTrace();
            PrintExceptionMsg.printSQLException(se);
        } finally {
            try {
                if(conn != null){
                    // 5. Close the connection.
                    conn.close();
                    conn = null;
                }
            } catch (SQLException se) {
                PrintExceptionMsg.printSQLException(se);
            }
        }

    }
}
