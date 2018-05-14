package top.kwseeker.JdbcExecuteSQL;

import org.junit.Test;
import top.kwseeker.JdbcConnection.Jdbc4Connection;

import java.sql.Connection;

import static org.junit.Assert.*;

public class JdbcRowSetsTest {
    Connection conn = new Jdbc4Connection("db_url", "user_name","password",
            "resources/mysql-conn-properties.xml").getConn();
    JdbcRowSets jdbcRowSets = new JdbcRowSets(conn);

    @Test
    public void testJdbcRowSet() throws Exception {
        jdbcRowSets.testJdbcRowSet();
    }

    @Test
    public void testCachedRowSet() throws Exception {
        jdbcRowSets.testCachedRowSet();
    }

}