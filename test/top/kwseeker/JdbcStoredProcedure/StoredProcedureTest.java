package top.kwseeker.JdbcStoredProcedure;

import org.junit.Test;
import top.kwseeker.JdbcConnection.Jdbc4Connection;

import java.sql.Connection;

import static org.junit.Assert.*;

public class StoredProcedureTest {
    Connection conn = new Jdbc4Connection("db_url", "user_name","password",
            "resources/mysql-conn-properties.xml").getConn();

    StoredProcedure myStoredProcedure = new StoredProcedure(
        conn, null, null);

    @Test
    public void runStoredProcedures() throws Exception {
        myStoredProcedure.createProcedureShowSuppliers();
        myStoredProcedure.createProcedureGetSupplierOfItem();
        myStoredProcedure.createProcedureRaisePrice();

        myStoredProcedure.runStoredProcedures("小米3", -0.2f, 1499);
    }

}