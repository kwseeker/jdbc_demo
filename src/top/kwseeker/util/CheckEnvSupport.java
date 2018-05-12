package top.kwseeker.util;

import top.kwseeker.JdbcConnection.Jdbc4Connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEnvSupport {

//    private Jdbc4Connection jdbc4Connection = null;
//    private Connection conn = null;

    public static void checkIsolationLevel(Connection conn) {
        try {
            DatabaseMetaData dm = conn.getMetaData();
            System.out.println("Transaction Isolation Level: \n"
                    + "\tDefaultTransactionIsolation: "
                    + dm.getDefaultTransactionIsolation() +"\n"
                    + "\tConnection transaction level: "
                    + conn.getTransactionIsolation());
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void checkResultSetSupport(Connection conn) {
        try {
            DatabaseMetaData dm = conn.getMetaData();
            System.out.println("Is this jdbc driver support: \n"
                    + "\tResultSet:TYPE_SCROLL_SENSITIVE: "
                    + dm.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE) +"\n"
                    + "\tResultSet:CONCUR_UPDATABLE: "
                    + dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void cursorHoldabilitySupport(Connection conn) throws SQLException {

        DatabaseMetaData dbMetaData = conn.getMetaData();

        System.out.println("Is this jdbc driver support: \n"
                + "\tResultSet.HOLD_CURSORS_OVER_COMMIT = "
                + ResultSet.HOLD_CURSORS_OVER_COMMIT + "\n"
                + "\tResultSet.CLOSE_CURSORS_AT_COMMIT = "
                + ResultSet.CLOSE_CURSORS_AT_COMMIT + "\n"
                + "\tDefault cursor holdability: "
                + dbMetaData.getResultSetHoldability() + "\n"
                + "\tSupports HOLD_CURSORS_OVER_COMMIT? "
                + dbMetaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT) + "\n"
                + "\tSupports CLOSE_CURSORS_AT_COMMIT? "
                + dbMetaData.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT) );
    }

}
