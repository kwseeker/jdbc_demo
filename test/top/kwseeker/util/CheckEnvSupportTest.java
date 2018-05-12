package top.kwseeker.util;

import org.junit.Test;
import top.kwseeker.JdbcConnection.Jdbc4Connection;

import java.sql.Connection;

public class CheckEnvSupportTest {
    Connection conn = new Jdbc4Connection("db_url", "user_name","password",
            "resources/mysql-conn-properties.xml").getConn();

    @Test
    public void checkResultSetSupport() throws Exception {
        CheckEnvSupport.checkResultSetSupport(conn);
    }

    @Test
    public void cursorHoldabilitySupport() throws Exception {
        CheckEnvSupport.cursorHoldabilitySupport(conn);
    }

    @Test
    public void checkIsolationLevel() throws Exception {
        CheckEnvSupport.checkIsolationLevel(conn);
    }

}