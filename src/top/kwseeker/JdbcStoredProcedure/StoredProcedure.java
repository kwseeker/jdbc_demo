package top.kwseeker.JdbcStoredProcedure;

import top.kwseeker.JdbcExecuteSQL.JdbcDBOperation;
import top.kwseeker.util.PrintExceptionMsg;

import java.sql.*;

public class StoredProcedure {

    private String dbms;
    private String dbName;
    private Connection con;

    public StoredProcedure(Connection connArg, String dbName,
                                      String dbmsArg) {
        super();
        this.dbms = dbmsArg;
        this.dbName = dbName;
        this.con = connArg;
    }

    private void executeDropAndUpdate(String queryDropStr, String createProcedureStr) throws SQLException {
        Statement stmt = null;
        Statement stmtDrop = null;
        try {
            System.out.println("Calling DROP PROCEDURE");
            stmtDrop = con.createStatement();
            stmtDrop.execute(queryDropStr);
        } catch (SQLException e) {
            PrintExceptionMsg.printSQLException(e);
        } finally {
            if (stmtDrop != null) {
                stmtDrop.close();
            }
        }

        try {
            stmt = con.createStatement();
            stmt.executeUpdate(createProcedureStr);
        } catch (SQLException e) {
            PrintExceptionMsg.printSQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /*=存储过程创建与注册===================================================================*/
    // 不带参数的存储过程 SHOW_SUPPLIERS()
    // 显示供应商和供应商品
    public void createProcedureShowSuppliers() throws SQLException{
        String queryDropStr = "drop procedure if exists SHOW_SUPPLIERS";
        String createProcedureStr =
            "create procedure SHOW_SUPPLIERS() " +
                "begin " +
                    "select SUPPLIERS.SUPPLIERNAME, ITEMS.NAME " +
                    "from SUPPLIERS, ITEMS " +
                    "where SUPPLIERS.SUPPLIERID = ITEMS.SUPPLIERID " +
                    "order by SUPPLIERNAME; " +
                "end";

        executeDropAndUpdate(queryDropStr, createProcedureStr);
    }

    // 带参数IN OUT的存储过程 GET_SUPPLIER_OF_ITEM
    // 获取某商品的供应商Item
    public void createProcedureGetSupplierOfItem() throws SQLException{

        String queryDropStr = "DROP PROCEDURE IF EXISTS GET_SUPPLIER_OF_ITEM";
        String createProcedureStr =
            "create procedure GET_SUPPLIER_OF_ITEM(IN itemName varchar(50), OUT supplierName varchar(64)) " +
                "begin " +
                "select SUPPLIERS.SUPPLIERNAME into supplierName " +
                "from SUPPLIERS, ITEMS " +
                "where SUPPLIERS.SUPPLIERID = ITEMS.SUPPLIERID " +
                "and itemName = ITEMS.NAME ; " +
                "select supplierName; " +
                "end";

        executeDropAndUpdate(queryDropStr, createProcedureStr);
    }

    // 带参数IN INOUT的存储过程 RAISE_PRICE
    // 涨价，最大涨幅 maximumPercentage=0.1
    public void createProcedureRaisePrice() throws SQLException {

        String queryDropStr = "DROP PROCEDURE IF EXISTS RAISE_PRICE";
        String createProcedureStr =
            "create procedure RAISE_PRICE(IN itemName varchar(50), IN maximumPercentage float, INOUT newPrice INT) " +
            "begin " +
                "main: BEGIN " +                                                        //使用标签
//                    "declare maximumNewPrice numeric(10,2); " +                         // 小数
                    "declare maximumNewPrice INT; " +                         // 小数
                    "declare oldPrice INT; " +
                    "select ITEMS.PRICE into oldPrice " +
                        "from ITEMS " +
                        "where ITEMS.name = itemName; " +
                    "set maximumNewPrice = oldPrice * (1 + maximumPercentage); " +
//                    "if (newPrice > maximumNewPrice) " +
//                    "then set newPrice = maximumNewPrice; " +
//                    "end if; " +
//                    "if (newPrice <= oldPrice) " +
//                    "then set newPrice = oldPrice;" +
//                    "leave main; " +
//                    "end if; " +
                    "if (ABS(newPrice-oldPrice) > ABS(oldPrice-maximumNewPrice)) " +
                    "then set newPrice = maximumNewPrice; " +
                    "end if; " +
                    "update ITEMS " +
                    "set ITEMS.PRICE = newPrice " +
                    "where ITEMS.NAME = itemName; " +
                    "select newPrice; " +
                "END main; " +
            "end";

        executeDropAndUpdate(queryDropStr, createProcedureStr);
    }

    /*=存储过程执行======================================================================*/
    // 执行上面的存储过程
    public void runStoredProcedures(String itemNameArg, float maximumPercentageArg, int newPriceArg) throws SQLException {
        CallableStatement cs = null;

        try {
            // 调用 GET_SUPPLIER_OF_ITEM
            System.out.println("\nCalling the procedure GET_SUPPLIER_OF_COFFEE");
            cs = this.con.prepareCall("{call GET_SUPPLIER_OF_ITEM(?, ?)}"); // IN OUT
            cs.setString(1, itemNameArg);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.executeQuery();

            String supplierName = cs.getString(2);
            if (supplierName != null) {
                System.out.println("\nSupplier of the coffee " + itemNameArg + ": " + supplierName);
            } else {
                System.out.println("\nUnable to find the coffee " + itemNameArg);
            }

            // 调用 SHOW_SUPPLIERS
            System.out.println("\nCalling the procedure SHOW_SUPPLIERS");
            cs = this.con.prepareCall("{call SHOW_SUPPLIERS}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String supplierNameStr = rs.getString("suppliername");
                String itemName = rs.getString("name");
                System.out.println(supplierNameStr + ": " + itemName);
            }

            // 调用 RAISE_PRICE
            System.out.println("\nContents of ITEMS table before calling RAISE_PRICE:");
            JdbcDBOperation jdbcDBOperation = new JdbcDBOperation();
            jdbcDBOperation.setConn(this.con);
            jdbcDBOperation.selectQuery();

            System.out.println("\nCalling the procedure RAISE_PRICE");
            cs = this.con.prepareCall("{call RAISE_PRICE(?,?,?)}");
            cs.setString(1, itemNameArg);
            cs.setFloat(2, maximumPercentageArg);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.setFloat(3, newPriceArg);
            cs.execute();
            System.out.println("\nValue of newPrice after calling RAISE_PRICE: " + cs.getFloat(3));

            System.out.println("\nContents of ITEMS table after calling RAISE_PRICE:");
            jdbcDBOperation.selectQuery();

        } catch (SQLException e) {
            PrintExceptionMsg.printSQLException(e);
        } finally {
            if (cs != null) { cs.close(); }
        }
    }
}
