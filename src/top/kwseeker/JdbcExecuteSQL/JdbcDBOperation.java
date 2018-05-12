package top.kwseeker.JdbcExecuteSQL;

import top.kwseeker.util.PrintExceptionMsg;

import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class JdbcDBOperation {

    private Connection conn;

    public JdbcDBOperation() {
//        super();
    }

    public JdbcDBOperation(Connection connArg) {
//        super();
        this.conn = connArg;
    }

    public void selectQuery() {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery("select id, name, supplierid, city, price, number, picture from items");

            Formatter formatter = new Formatter(System.out);
            formatter.format("%-3s\t %-11s\t %-7s\t %-11s\t %-7s\t %-7s\t %-11s\t\n", "id", "name", "supplierid", "city", "price", "number", "picture");
            while (resultSet.next()) {
                formatter.format("%-3d\t %-11s\t %-7s\t %-11s\t %-7d\t %-7d\t %-11s\t\n",
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("supplierid"),
                        resultSet.getString("city"),
                        resultSet.getInt("price"),
                        resultSet.getInt("number"),
                        resultSet.getString("picture"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertRow(String name, String city, int price,
                          int number, String picture)
            throws SQLException {

        Statement stmt = null;
//        String dbName = "shopping";
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet upResultSet = stmt.executeQuery(
                    "SELECT * FROM items");

            upResultSet.last();
            System.out.println("Row num: " + upResultSet.getRow());
            int newRowNum = upResultSet.getInt("id") + 1;

            upResultSet.moveToInsertRow();
            System.out.println("Row num: " + upResultSet.getRow());
            upResultSet.updateInt("id", newRowNum);
            upResultSet.updateString("name", name);
            upResultSet.updateString("city", city);
            upResultSet.updateInt("price", price);
            upResultSet.updateInt("number", number);
            upResultSet.updateString("picture", picture);

            upResultSet.insertRow();
            System.out.println("Row num: " + upResultSet.getRow());
            upResultSet.beforeFirst();  //回到第一行之前
            System.out.println("Row num: " + upResultSet.getRow());
        } catch (SQLException e ) {
            PrintExceptionMsg.printSQLException(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    // 带参数（？）的SQL语句 + 事务管理
    public void updateStorage(HashMap<String, Integer> stock) throws SQLException {

        String dbName = "shopping";
        String table = "items";
        PreparedStatement updateStorage = null;

        String updateStorageString =
                "update " + dbName + "." + table + " set number = number + ? where name = ?";

        try {
            conn.setAutoCommit(false);
            updateStorage = conn.prepareStatement(updateStorageString);
            conn.setSavepoint();        // 设置回退点
            for (Map.Entry<String, Integer> e : stock.entrySet()) { // entry 迭代器
//                updateStorage.setInt(1, e.getValue().intValue()); // 无需 unbox
                updateStorage.setInt(1, e.getValue());
                updateStorage.setString(2, e.getKey());
                updateStorage.executeUpdate();
//                conn.commit();
            }
            conn.commit();
        } catch (SQLException e ) {
            PrintExceptionMsg.printSQLException(e);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch(SQLException se) {
                    PrintExceptionMsg.printSQLException(se);
                }
            }
        } finally {
            if (updateStorage != null) {
                updateStorage.close();
            }
            conn.setAutoCommit(true);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
